#! /bin/bash

TO_VERSION=$1

function update() {
    from=$1
    to=$2
    sed -e "s/\${VERSION}/${TO_VERSION}/g" -e "s/\${VERSION_DH}/${TO_VERSION//-/--}/g" $from > $to
}

for i in .templates/*
do
    update $i ${i#.templates/}
done