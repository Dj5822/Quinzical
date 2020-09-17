#!/bin/bash

# Echos all the categories that exist in the categories folder.
for category in `ls categories`; do
	echo ${category%.*}
done
