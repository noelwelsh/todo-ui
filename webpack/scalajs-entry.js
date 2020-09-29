if (process.env.NODE_ENV === "production") {
    const opt = require("./todo-ui-opt.js");
    opt.main();
    module.exports = opt;
} else {
    var exports = window;
    exports.require = require("./todo-ui-fastopt-entrypoint.js").require;
    window.global = window;

    const fastOpt = require("./todo-ui-fastopt.js");
    fastOpt.main()
    module.exports = fastOpt;

    if (module.hot) {
        module.hot.accept();
    }
}
