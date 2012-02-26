/*******************************************************************************
 * This file is part of the Symfony eclipse plugin.
 *
 * (c) Robert Gruendler <r.gruendler@gmail.com>
 *
 * For the full copyright and license information, please view the LICENSE
 * file that was distributed with this source code.
 ******************************************************************************/
package com.dubture.symfony.ui.editor.highlighting;

import java.util.List;

import org.eclipse.dltk.core.ModelException;
import org.eclipse.php.internal.core.ast.nodes.Comment;
import org.eclipse.php.internal.core.codeassist.strategies.PHPDocTagStrategy;
import org.eclipse.php.internal.ui.Logger;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticApply;
import org.eclipse.php.internal.ui.editor.highlighter.AbstractSemanticHighlighting;
import org.eclipse.swt.graphics.RGB;

import com.dubture.symfony.annotation.model.Annotation;
import com.dubture.symfony.annotation.parser.AnnotationCommentParser;
import com.dubture.symfony.annotation.parser.antlr.SourcePosition;

/**
 *
 * Highlighting for Annotations.
 *
 */
@SuppressWarnings("restriction")
public class AnnotationHighlighting extends AbstractSemanticHighlighting {

    protected class AnnotationApply extends AbstractSemanticApply {

        @Override
        public boolean visit(Comment comment) {
            if (comment.getCommentType() != Comment.TYPE_PHPDOC) {
                return true;
            }

            try {
                int commentStartOffset = comment.getStart();
                int commentEndOffset = comment.getStart() + comment.getLength();
                String source = getSourceModule().getSource();
                String commentSource = source.substring(commentStartOffset, commentEndOffset);

                AnnotationCommentParser parser = new AnnotationCommentParser(commentSource, commentStartOffset);
                parser.setExcludedClassNames(PHPDocTagStrategy.PHPDOC_TAGS);

                List<Annotation> annotations = parser.parse();

                for (Annotation annotation : annotations) {
                    SourcePosition sourcePosition = annotation.getSourcePosition();
                    highlight(sourcePosition.startIndex, sourcePosition.length);
                }
            } catch (ModelException exception) {
                Logger.logException(exception);
            }

            return true;
        }
    }

    public AnnotationHighlighting() {
        super();
    }

    @Override
    public String getDisplayName() {

        return "Annotations";

    }

    @Override
    public AbstractSemanticApply getSemanticApply() {

        return new AnnotationApply();
    }

    @Override
    public void initDefaultPreferences() {

        getStyle().setUnderlineByDefault(false)
         .setDefaultTextColor(new RGB(64, 64, 64));

    }
}
