from os.path import dirname, exists, join

import pythonforandroid
from pythonforandroid.recipes.python3 import Python3Recipe


class UpstreamPatchMixin:
    def apply_patch(self, filename, arch, build_dir=None):
        local_patch = join(self.get_recipe_dir(), filename)
        if not exists(local_patch):
            upstream_recipe_dir = join(
                dirname(pythonforandroid.__file__), "recipes", "python3"
            )
            filename = join(upstream_recipe_dir, filename)
        return super().apply_patch(filename, arch, build_dir)


class Python313Recipe(UpstreamPatchMixin, Python3Recipe):
    version = "3.13.11"


recipe = Python313Recipe()
