from os.path import dirname, join

import pythonforandroid
from pythonforandroid.recipes.kivy import KivyRecipe


p4a_recipe_dir = join(dirname(pythonforandroid.__file__), "recipes", "kivy")


class Python314KivyRecipe(KivyRecipe):
    hostpython_prerequisites = ["cython>=3.1.2", "setuptools"]
    patches = [
        (join(p4a_recipe_dir, patch[0]), patch[1])
        if isinstance(patch, tuple)
        else join(p4a_recipe_dir, patch)
        for patch in KivyRecipe.patches
    ]


recipe = Python314KivyRecipe()
