#!/bin/bash

# Echos all the categories that exist in the categories folder.
for category in "categories/nz"/*; do
	path=${category%.*}
	echo ${path##*/}
done
