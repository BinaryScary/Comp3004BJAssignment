#!/bin/bash
for file in *.png; do
    string=$file
    char1=${string:0:1}
    char2=${string:1:1}
    ext=".png"
    string="$char2$char1$ext"
    mv $file $string
done
