#!/bin/bash


if [ $# -eq 0 ]; then
	echo "You must input a category."
else
	shuf -n 1 categories/"$1".txt
fi

