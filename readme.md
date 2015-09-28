Reproducer demo case for using `vertx3-eventbus-client` in a Webpack build.

## Server

The server is just enough to serve the client and do a minimal thing on the event bus.
It runs http server on port 9999.

Build and run the usual way.

```bash

gradle shadowJar
java -jar build/libs/vertx-client-webpack-reproducer-3.0.0-fat.jar
```
Static files served from the webroot of the project's root directory.

## Client

The client is a ReactJS/Apt/Vertx app that's built with Webpack.

When the build is working, you can visit

http://localhost:9999/index.html

The page will show a time readout that is updated by the vertx server every second.

There are build scripts in the package.json, so build in the usual NPM way...

```bash
cd client
npm install
npm run devbuild
```

This will build the client and put it in the server's webroot.  


## Problem

As things are now, the `npm run devbuild` fails for the reasons indicated in 
issue 195  https://github.com/vert-x3/vertx-web/issues/195

```
[0] % npm run devbuild

> ecco-ops@1.0.0 devbuild /Volumes/Work/projects/vertx-client-webpack-reproducer/client
> TARGET=dev webpack

Hash: 5b2346342f7eff19d9d3
Version: webpack 1.12.2
Time: 2056ms
     Asset       Size  Chunks             Chunk Names
 bundle.js     852 kB       0  [emitted]  main
index.html  190 bytes          [emitted]
    + 203 hidden modules

ERROR in ./~/vertx3-eventbus-client/vertxbus.js
Module not found: Error: Cannot resolve module 'sockjs' in /Volumes/Work/projects/vertx-client-webpack-reproducer/client/node_modules/vertx3-eventbus-client
 @ ./~/vertx3-eventbus-client/vertxbus.js 24:4-43
```


To demonstrate that this app can work, make the following changes in file
`client/app/stores/VertxStore.js`.


From 

```javascript
var getEventBus = () =>
{
// This is my monkeypatched vertx client
// const Vertx = require("../vendor/vertxbus.js");

// And this is the client if retrieved by npm
  const Vertx = require('vertx3-eventbus-client')

  return (new Vertx('http://localhost:9999/eventbus'));
};
```

to

```javascript
var getEventBus = () =>
{
// This is my monkeypatched vertx client
  const Vertx = require("../vendor/vertxbus.js");

// And this is the client if retrieved by npm
//  const Vertx = require('vertx3-eventbus-client')

  return (new Vertx('http://localhost:9999/eventbus'));
};
```


As you can see, this will load the vertx3 client that I've placed in 
`client/vendor.vertxbus.js`.  No changes have been made that impact the
sockjs dependency.  The vendored file is the one described in issue 195, 
it merely reverses the order of the check for the amd and commonjs loaders so
the correct sockjs dependency (commonjs, rather than apm) is used.

