"use strict";

var TreeModel = require("tree-model"),
    fs = require("fs-extra"),
    model = new TreeModel;

if (process.argv.length < 3) {
    console.log("Usage: node issue-sorter <input_json>")
    process.exit(1);
}

fs.readJson(process.argv[2])
    .then(obj => {
        if (!obj.states || !obj.issues) {
            console.error("Malformed input file.");
            return;
        }
        if (obj.states.length < 1 || obj.issues.length < 1) {
            console.error("Empty data.");
            return;
        }
        let root_state;
        obj.states.forEach((state_pair, idx) => {
            if (idx === 0) {
                root_state = model.parse({id: state_pair.from, issues: [], children:[{id: state_pair.to, issues: []}]});
                return;
            }
            let node = root_state.first(nd => (nd.model.id === state_pair.from));
            let leaf = root_state.first(nd => (nd.model.id === state_pair.to));
            if (!node) {
                if (leaf === root_state) {
                    let temp = model.parse({id: state_pair.from, issues: []});
                    temp.addChild(root_state);
                    root_state = temp;
                } else {
                    node = root_state.addChild(model.parse({id: state_pair.from, issues: []}));
                }
            }
            if (!leaf) {
                node.addChild(model.parse({id: state_pair.to, issues: []}));
            }
        });
        root_state.walk(nd => {
            if (!nd.hasChildren()) {
                nd.setIndex(0);
            }
        });
        obj.issues.forEach(issue => {
            let node = root_state.first(nd => (nd.model.id === issue.state));
            node.model.issues.push(issue.issue);
        });
        root_state.walk(nd => console.log(nd.model.id, nd.model.issues));
    })
    .catch(err => console.error("Unable to read input file", err));
