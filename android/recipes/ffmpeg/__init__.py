import importlib.util
from os.path import dirname, join

import pythonforandroid


p4a_recipe_dir = join(
    dirname(pythonforandroid.__file__),
    "recipes",
    "ffmpeg",
)
recipe_spec = importlib.util.spec_from_file_location(
    "pythonforandroid.recipes.ffmpeg_base",
    join(p4a_recipe_dir, "__init__.py"),
)
if recipe_spec is None or recipe_spec.loader is None:
    raise ImportError("Cannot load python-for-android's FFmpeg recipe")
recipe_module = importlib.util.module_from_spec(recipe_spec)
recipe_spec.loader.exec_module(recipe_module)


class FFmpeg61Recipe(recipe_module.FFMpegRecipe):
    version = "n6.1.2"
    url = "https://github.com/FFmpeg/FFmpeg/archive/{version}.zip"
    patches = []

    @staticmethod
    def get_base_configure_flags():
        return recipe_module.FFMpegRecipe.get_base_configure_flags() + [
            "--disable-vulkan",
        ]


recipe = FFmpeg61Recipe()
