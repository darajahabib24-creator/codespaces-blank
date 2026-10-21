from os.path import dirname, join

import pythonforandroid
from pythonforandroid.recipes.ffpyplayer import FFPyPlayerRecipe


p4a_recipe_dir = join(
    dirname(pythonforandroid.__file__), "recipes", "ffpyplayer"
)


class FFmpeg61FFPyPlayerRecipe(FFPyPlayerRecipe):
    patches = [
        join(p4a_recipe_dir, "setup.py.patch"),
        "ffmpeg-8.patch",
        "python-3.14.patch",
        "python-3.14-cython.patch",
        "python-3.14-callbacks.patch",
    ]


recipe = FFmpeg61FFPyPlayerRecipe()
