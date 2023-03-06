#! /bin/sh

DIST=$1

mkdir -p $DIST/bin
cp -r README.md LICENSE build/libs build/completions $DIST 
cp .bin/plmaker $DIST/bin

