#!/bin/bash


if [ $# -eq 0 ]; then
	echo "You must input a category."
else
	shuf -n 5 categories/"$2"/"$1".txt
fi

