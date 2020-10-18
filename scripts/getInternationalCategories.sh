#!/bin/bash

# Echos all the categories that exist in the categories folder.
for category in "categories/international"/*; do
	path=${category%.*}
	echo ${path##*/}
done
