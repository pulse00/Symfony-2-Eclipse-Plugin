package com.dubture.symfony.core.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipUtils;
import org.apache.commons.compress.utils.IOUtils;

public final class UncompressUtils {

    private static final int BUFFER_SIZE = 8 * 1024;

    private UncompressUtils() {
        // Cannot instantiate
    }

    /**
     * Uncompress a gzip archive and returns the file where it has been
     * extracted.
     *
     * @param archiveFile The archive file to uncompress
     * @param outputDirectory The output directory where to put the uncompressed archive
     *
     * @return The output file where the archive has been uncompressed
     *
     * @throws IOException When a problem occurs with either the input or output stream
     */
    public static File uncompressGzipArchive(File archiveFile, File outputDirectory) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(archiveFile);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        GzipCompressorInputStream gzipInputStream = new GzipCompressorInputStream(bufferedInputStream);

        String tarArchiveFilename = GzipUtils.getUncompressedFilename(archiveFile.getName());
        File outputFile = new File(outputDirectory, tarArchiveFilename);
        FileOutputStream outputStream = new FileOutputStream(outputFile);

        int byteReadCount = 0;
        final byte[] data = new byte[BUFFER_SIZE];

        try {
            while ((byteReadCount = gzipInputStream.read(data, 0, BUFFER_SIZE)) != -1) {
                outputStream.write(data, 0, byteReadCount);
            }
        } finally {
            outputStream.close();
            gzipInputStream.close();
        }

        return outputFile;
    }

    /**
     * Uncompress all entries found in a tar archive file into the given output
     * directory.
     *
     * @param archiveFile The tar archive file to uncompress
     * @param outputDirectory The output directory where to put uncompressed entries
     *
     * @throws IOException When an error occurs while uncompressing the tar archive
     */
    public static void uncompressTarArchive(File archiveFile, File outputDirectory) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(archiveFile);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        TarArchiveInputStream tarInputStream = new TarArchiveInputStream(bufferedInputStream);

        try {
            TarArchiveEntry tarEntry = null;
            while ((tarEntry = tarInputStream.getNextTarEntry()) != null) {
                uncompressTarArchiveEntry(tarInputStream, tarEntry, outputDirectory);
            }
        } finally {
            tarInputStream.close();
        }
    }

    /**
     * Uncompress a tar archive entry to the specified output directory.
     *
     * @param tarInputStream The tar input stream associated with the entry
     * @param entry The tar archive entry to uncompress
     * @param outputDirectory The output directory where to put the uncompressed entry
     *
     * @throws IOException If an error occurs within the input or output stream
     */
    public static void uncompressTarArchiveEntry(TarArchiveInputStream tarInputStream,
                                                 TarArchiveEntry entry,
                                                 File outputDirectory) throws IOException {
        // TODO: This is specific to the symfony library archive, should be made
        //       fully generic.
        String entryName = stripEntryNamePrefix(entry.getName());

        if (entry.isDirectory()) {
            createDirectory(new File(outputDirectory, entryName));

            return;
        }

        File outputFile = new File(outputDirectory, entryName);
        ensureDirectoryHierarchyExists(outputFile);

        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(outputFile));

        try {
            IOUtils.copy(tarInputStream, outputStream);
            addExecutableBit(outputFile, Permissions.fromMode(entry.getMode()));
        } finally {
            outputStream.close();
        }
    }

    /**
     * Add the executable bit to a file. The file must not be a directory otherwise
     * false is returned. If the executable bit has been applied successfully, the
     * method returns true. If the permissions specify
     *
     * @param file The file to add executable bit to
     * @param permissions The permissions associated with the file
     *
     * @return True if the application succeeded, false otherwise
     */
    private static boolean addExecutableBit(File file, Permissions permissions) {
        if (file.isDirectory() || !permissions.hasExecutableBit()){
            return false;
        }

        return file.setExecutable(true, permissions.hasOnlyOwnerExecutableBit());
    }

    /**
     * Method that creates the directory structure of the directory
     * received in argument.
     *
     * @param directory The directory for which directory structure should be created
     */
    private static void createDirectory(File directory) {
        if (directory.exists()) {
            // Directory already exists, no need to do anything
            return;
        }

        if (!directory.mkdirs()) {
            throw new RuntimeException("Cannot create directory " + directory);
        }
    }

    /**
     * This will ensure the hierarchy of parent directories by creating
     * them if they do not exist.
     *
     * @param outputFile The output file for which hierarchy must exists
     */
    private static void ensureDirectoryHierarchyExists(File outputFile) {
        if (!outputFile.getParentFile().exists()){
            createDirectory(outputFile.getParentFile());
        }
    }

    /**
     * This method will remove the Symfony prefix from the zip entry.
     *
     * @return An entry name with the Symfony prefix removed if present
     */
    private static String stripEntryNamePrefix(String entryName) {
        if (entryName.startsWith("Symfony")) {
            entryName = entryName.replace("Symfony", "");
        }

        return entryName;
    }

    /**
     * A class used to wrap permissions. This class uses unix
     * style permissions encoding. It is used mainly by the UncompressUtils
     * class to add executable bit to extracted file if they have it
     * in the archive. This class is incomplete and does the work
     * only for executable bit for now.
     *
     * @author Matthieu Vachon <matthieu.o.vachon@gmail.com>
     */
    protected static class Permissions {

        /** Mask for the three least significant bits */
        private static final int PACK_MASK = 7;

        /** Mask for the least significant bit */
        private static final int EXECUTABLE_MASK = 1;

        int mode;

        boolean hasExecutableBitOwner;
        boolean hasExecutableBitGroup;
        boolean hasExecutableBitOther;

        public static Permissions fromMode(int mode) {
            return new Permissions(mode);
        }

        public static Permissions fromUnixMode(String mode) {
            return new Permissions(Integer.parseInt(mode, 8));
        }

        private Permissions(int mode) {
            this.mode = mode;

            setOwner(getBitsPack(0));
            setGroup(getBitsPack(1));
            setOther(getBitsPack(2));
        }

        /**
         * Check if the permissions have an executable bit in any of
         * the three possible pack: owner, group and other.
         *
         * @return True if the permissions has executable bit for any packs
         */
        public boolean hasExecutableBit() {
            return hasExecutableBitOwner || hasExecutableBitGroup || hasExecutableBitOther;
        }

        /**
         * Check if the permissions have executable bit only for the owner of
         * the file.
         *
         * @return True if the permissions has executable bit only for owner
         */
        public boolean hasOnlyOwnerExecutableBit() {
            return hasExecutableBitOwner && !hasExecutableBitGroup && !hasExecutableBitOther;
        }

        private void setOwner(int ownerPack) {
            hasExecutableBitOwner = (ownerPack & EXECUTABLE_MASK) == 1;
        }

        private void setGroup(int groupPack) {
            hasExecutableBitGroup = (groupPack & EXECUTABLE_MASK) == 1;
        }

        private void setOther(int otherPack) {
            hasExecutableBitOther = (otherPack & EXECUTABLE_MASK) == 1;
        }

        private int getBitsPack(int packId) {
            assert(packId > 0 && packId <= 2);

            int offset = packId * 3;

            return (this.mode >> offset) & PACK_MASK;
        }

        public String toString() {
            String owner = "Owner: " + (hasExecutableBitOwner ? "x" : "-");
            String group = "Group: " + (hasExecutableBitGroup ? "x" : "-");
            String other = "Other: " + (hasExecutableBitOther ? "x" : "-");

            return owner + ", " + group + ", " + other;
        }
    }
}
