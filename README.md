You can find the project status [here](https://github.com/users/vskurikhin/projects/4).

# DayBook
Day Book written on JavaEE/ThornTail with PrimeFaces.

## Table of Contents
- [New Releases](#new-releases)
- [Available Scripts](#available-scripts)
  - [gradlew clean build ](#gradlew)
  - [gradlew update](#gradlew-update)
  - [gradlew generateChangelog](#gradlew-generateChangelog)
  - [gradlew integrationTest](#gradlew-integrationTest)
- [Something Missing?](#something-missing)

## New Releases
Start.

## Available Scripts `gradlew`

In the project directory, you can build by:
Builds the app for production to the `gradlew clean build` folder.<br>
It correctly bundles DayBook in production mode and optimizes the build for the best performance.

### `gradlew update`
```
  ./gradlew update
```

### `gradlew generateChangelog`
```
  ./gradlew generateChangelog -PrunList='gen_dictionary, gen_db'
```
### `gradlew integrationTest`

In the project directory, you can integration test by:
```
  VERSION_THORNTAIL=2.5.0.Final ./gradlew clean integrationTest -Plocal=true
```

## Something Missing?

If you have ideas for more “How To” recipes that should be on this page, [let us know](https://github.com/vskurikhin/daybook/issues).
