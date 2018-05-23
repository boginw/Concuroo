#!/usr/bin/env bash
# https://github.com/uncrustify/uncrustify
git clone git@github.com:uncrustify/uncrustify.git
cd uncrustify
mkdir build
cd build
cmake ..
cmake --build .
sudo cp uncrustify /usr/local/bin
cd ..
cd ..