# Mondradiko Java Bundler
Makes managing the AssemblyScript environment for Mondradiko easier.
This utility is a companion for anyone looking to create and modify their own world with Mondradiko.
It manages Mondradiko dependencies for you and streamlines the process of building a world.

## Quick start guide
The first time you launch this application, you will need to specify the location of several important executables related to Mondradiko:
- The bundler executable - this is used to bundle all your assets into binaries that the mondradiko server can read
- The codegen directory - this is the folder that contains `generate_class.py` and subdirectories for `.toml` and `.ts` files that form the assemblyscript environment
- The client executable - this is what is called when you launch the latest version of your project
- The client's config.toml (optional) - this would override the config.toml, otherwise the mondradiko client will look for one in the same directory as the client executable

You should also ensure you have the following installed:
- python
- assemblyscript (make sure it is on your path, e.g. `npm install assemblyscript --global`
- npm (optional, but how would you install assemblyscript without npm)

Given all the dependencies are met, if you create a new project with the "Setup Project Environment" button, the environment will be created ready for you to use with VSCode and your other preferred means of editing game assets. When launching this Java utility again, you will need to open the project by choosing the `mondradiko.proj` file in the root of your project directory.

### VSCode additional steps
Open the folder that represents the root of the project you created. If you have the TypeScript extension installed, vscode will validate your code for you before you try to compile it.
