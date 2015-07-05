#!/bin/bash
#Sample Usage: pushToBintray.sh username apikey owner repo package version pathToP2Repo
API=https://api.bintray.com
BINTRAY_USER=$1
BINTRAY_API_KEY=$2
BINTRAY_OWNER=$3
BINTRAY_REPO=$4
PCK_NAME=$5
PCK_VERSION=$6
PATH_TO_REPOSITORY=$7

function main() {
deploy_updatesite
}

function deploy_updatesite() {
echo "${BINTRAY_USER}"
echo "${BINTRAY_API_KEY}"
echo "${BINTRAY_OWNER}"
echo "${BINTRAY_REPO}"
echo "${PCK_NAME}"
echo "${PCK_VERSION}"
echo "${PATH_TO_REPOSITORY}"

if [ ! -z "$PATH_TO_REPOSITORY" ]; then
   cd "$PATH_TO_REPOSITORY"
fi

echo "Drop previous version"
curl -X DELETE -u ${BINTRAY_USER}:${BINTRAY_API_KEY} https://api.bintray.com/packages/${BINTRAY_OWNER}/${BINTRAY_REPO}/${PCK_NAME}/versions/${PCK_VERSION}
echo ""

FILES=./*
BINARYDIR=./binary/*
PLUGINDIR=./plugins/*
FEATUREDIR=./features/*

for f in $FILES;
do
if [ ! -d $f ]; then
  echo "Processing $f file..."
curl -X PUT -T "$f" -u ${BINTRAY_USER}:${BINTRAY_API_KEY} -H "X-Bintray-Package:$PCK_NAME" -H "X-Bintray-Version:$PCK_VERSION" -H "X-Bintray-Publish:0" -H "X-Bintray-Override:1" https://api.bintray.com/content/${BINTRAY_OWNER}/${BINTRAY_REPO}/${PCK_NAME}/${PCK_VERSION}/$f

  echo ""
fi
done

echo "Processing features dir $FEATUREDIR file..."
for f in $FEATUREDIR;
do
  echo "Processing feature: $f file..."
  curl -X PUT -T "$f" -u ${BINTRAY_USER}:${BINTRAY_API_KEY} -H "X-Bintray-Package:$PCK_NAME" -H "X-Bintray-Version:$PCK_VERSION" -H "X-Bintray-Publish:0" -H "X-Bintray-Override:1" https://api.bintray.com/content/${BINTRAY_OWNER}/${BINTRAY_REPO}/${PCK_NAME}/${PCK_VERSION}/features/`basename $f`
  echo ""
done

echo "Processing plugin dir $PLUGINDIR file..."

for f in $PLUGINDIR;
do
   # take action on each file. $f store current file name
  echo "Processing plugin: $f file..."
  curl -X PUT -T "$f" -u ${BINTRAY_USER}:${BINTRAY_API_KEY} -H "X-Bintray-Package:$PCK_NAME" -H "X-Bintray-Version:$PCK_VERSION" -H "X-Bintray-Publish:0" -H "X-Bintray-Override:1" https://api.bintray.com/content/${BINTRAY_OWNER}/${BINTRAY_REPO}/${PCK_NAME}/${PCK_VERSION}/plugins/`basename $f`
  echo ""
done

echo "Publishing the new version"
curl -X POST -u ${BINTRAY_USER}:${BINTRAY_API_KEY} https://api.bintray.com/content/${BINTRAY_OWNER}/${BINTRAY_REPO}/${PCK_NAME}/${PCK_VERSION}/publish -d "{ \"discard\": \"false\" }"

}


main "$@"

