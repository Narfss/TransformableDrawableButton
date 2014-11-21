rm -dR publish
mkdir publish

#ic_launcher_web
inkscape --export-width=512 --export-height=512 ic_launcher.svg --export-png=publish/ic_launcher.png

#feature_graphic
inkscape feature_graphic.svg --export-png=publish/feature_graphic.png

rm -d ../app/src/main/res/drawable-mdpi/*
rm -d ../app/src/main/res/drawable-hdpi/*
rm -d ../app/src/main/res/drawable-xhdpi/*
rm -d ../app/src/main/res/drawable-xxhdpi/*
rm -d ../app/src/main/res/drawable-xxxhdpi/*


#ic_launcher
inkscape --export-width=192 --export-height=192 ic_launcher.svg --export-png=../app/src/main/res/drawable-xxxhdpi/ic_launcher.png
inkscape --export-width=144 --export-height=144 ic_launcher.svg --export-png=../app/src/main/res/drawable-xxhdpi/ic_launcher.png
inkscape --export-width=96 --export-height=96 ic_launcher.svg --export-png=../app/src/main/res/drawable-xhdpi/ic_launcher.png
inkscape --export-width=72 --export-height=72 ic_launcher.svg --export-png=../app/src/main/res/drawable-hdpi/ic_launcher.png
inkscape --export-width=48 --export-height=48 ic_launcher.svg --export-png=../app/src/main/res/drawable-mdpi/ic_launcher.png


#inkscapeic_launcher.svg drawable-xxxhdpi/ic_launcher.png
#inkscape--export-width=75% ic_launcher.svg drawable-xxhdpi/ic_launcher.png
#inkscape--export-width=50% ic_launcher.svg drawable-xhdpi/ic_launcher.png
#inkscape--export-width=37.5% ic_launcher.svg drawable-hdpi/ic_launcher.png
#inkscape--export-width=25% ic_launcher.svg drawable-mdpi/ic_launcher.png
