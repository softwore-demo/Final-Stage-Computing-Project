

layui.define(["jquery", "laytpl"], function (exports) {
    var $ = layui.jquery;
    var laytpl = layui.laytpl;
    var hint = layui.hint();

    var MOD_NAME = "eleTree";

    var eleTree = {

        on: function (events, callback) {
            return layui.onevent.call(this, MOD_NAME, events, callback);
        },
        render: function (options) {
            var inst = new Class(options);
            return thisTree.call(inst);
        }
    }

    var thisTree = function () {
        var _self = this;
        var options = _self.config;


        return {

            updateKeyChildren: function (key, data) {
                if (options.data.length === 0) return;
                return _self.updateKeyChildren.call(_self, key, data);
            },
            updateKeySelf: function (key, data) {
                if (options.data.length === 0) return;
                return _self.updateKeySelf.call(_self, key, data);
            },
            remove: function (key) {
                if (options.data.length === 0) return;
                return _self.remove.call(_self, key);
            },
            append: function (key, data) {
                if (options.data.length === 0) return;
                return _self.append.call(_self, key, data);
            },
            insertBefore: function (key, data) {
                if (options.data.length === 0) return;
                return _self.insertBefore.call(_self, key, data);
            },
            insertAfter: function (key, data) {
                if (options.data.length === 0) return;
                return _self.insertAfter.call(_self, key, data);
            },

            getChecked: function (leafOnly, includeHalfChecked) {
                if (options.data.length === 0) return;
                return _self.getChecked.call(_self, leafOnly, includeHalfChecked);
            },

            setChecked: function (data, isReset) {
                if (options.data.length === 0) return;
                return _self.setChecked.call(_self, data, isReset);
            },

            unCheckNodes: function () {
                if (options.data.length === 0) return;
                return _self.unCheckNodes.call(_self);
            },
            unCheckArrNodes: function (data) {
                if (options.data.length === 0) return;
                return _self.unCheckArrNodes.call(_self, data);
            },
            expandAll: function () {
                options.elem.children(".eleTree-node").children(".eleTree-node-group").empty();
                _self.expandAll.call(_self, options.data, [], 1, true);
                _self.unCheckNodes(true);
                _self.defaultChecked();
                _self.checkboxInit();
            },
            unExpandAll: function () {
                return _self.unExpandAll.call(_self);
            },
            reload: function (options) {
                return _self.reload.call(_self, options);
            },
            search: function (value) {
                return _self.search.call(_self, value);
            }
        }
    }


    var TPL_ELEM = function (options, floor, parentStatus) {
        return [
            '{{# for(var i=0;i<d.length;i++){ }}',
            '<div class="eleTree-node {{# if(d[i].visible===false){ }}eleTree-search-hide{{# } }}" data-' + options.request.key + '="{{d[i]["' + options.request.key + '"]}}" eletree-floor="' + floor + '" style="display: none;">',
            '<div class="eleTree-node-content" type="{{d[i]["' + options.request.data + '"].type}}" style="padding-left: ' + (options.indent * floor) + 'px;">',
            '<span class="eleTree-node-content-icon">',
            '<i class="layui-icon layui-icon-triangle-r ',
            function () {
                if (options.lazy) {
                    var str = [
                        '{{# if(!d[i]["' + options.request.isLeaf + '"]){ }}',
                        'lazy-icon" ></i>',
                        '{{# }else{ }}',
                        'leaf-icon" style="color: transparent;" ></i>',
                        '{{# } }}'
                    ].join("");
                    return str;
                }
                return ['{{# if(!d[i]["' + options.request.children + '"] || d[i]["' + options.request.children + '"].length===0){ }}',
                    'leaf-icon" style="color: transparent;"',
                    '{{# } }}',
                    '"></i>'
                ].join("");
            }(),
            '</span>',
            function () {
                if (options.showCheckbox) {
                    var status = "";
                    if (parentStatus === "1") {
                        status = '"1" checked';
                    } else if (parentStatus === "2") {
                        status = '"2"';
                    } else {
                        status = '"0"';
                    }
                    return [
                        '{{# if(d[i]["' + options.request.checked + '"]) { }}',
                        '<input type="checkbox" name="eleTree-node" lay-ignore eleTree-status="1" checked data-checked class="layui-hide eleTree-hideen ',
                        '{{# }else{ }}',
                        '<input type="checkbox" name="eleTree-node" lay-ignore eleTree-status=' + status + ' class="layui-hide eleTree-hideen ',
                        '{{# } }}',

                        '{{# if(d[i]["' + options.request.disabled + '"]) { }}',
                        'eleTree-disabled',
                        '{{# } }}',
                        '" />'
                    ].join("");
                }
                return ''
            }(),
            '<span class="eleTree-node-content-label">{{d[i]["' + options.request.name + '"]}}</span>',
            '</div>',
            '<div class="eleTree-node-group">',
            '</div>',
            '</div>',
            '{{# } }}'
        ].join("");
    }

    var TPL_NoText = function () {
        return '<h3 class="eleTree-noText" style="text-align: center;height: 30px;line-height: 30px;color: #888;">{{d.emptText}}</h3>';
    }

    var Class = function (options) {
        options.response = $.extend({}, this.config.response, options.response);
        options.request = $.extend({}, this.config.request, options.request);
        this.config = $.extend({}, this.config, options);
        this.prevClickEle = null;
        this.nameIndex = 1;
        this.render();
    };

    Class.prototype = {
        constructor: Class,
        config: {
            elem: "",
            data: [],
            emptText: "No data",
            renderAfterExpand: true,
            highlightCurrent: false,
            defaultExpandAll: false,
            expandOnClickNode: true,
            checkOnClickNode: false,
            defaultExpandedKeys: [],
            autoExpandParent: true,
            showCheckbox: false,
            checkStrictly: false,
            defaultCheckedKeys: [],
            accordion: false,
            indent: 16,
            lazy: false,
            load: function () {
            },
            draggable: false,
            contextmenuList: [],
            searchNodeMethod: null,

            method: "get",
            url: "",
            contentType: "",
            headers: {},
            done: null,

            response: {
                statusName: "code",
                statusCode: 0,
                dataName: "data"
            },
            request: {
                name: "label",
                key: "id",
                children: "children",
                disabled: "disabled",
                checked: "checked",
                isLeaf: "isLeaf"
            }
        },
        render: function () {
            if (this.config.indent > 30) {
                this.config.indent = 30;
            } else if (this.config.indent < 10) {
                this.config.indent = 10;
            }
            var options = this.config;
            options.where = options.where || {};
            if (!options.elem) return hint.error("lack elem");
            options.elem = typeof options.elem === "string" ? $(options.elem) : options.elem;
            this.filter = options.elem.attr("lay-filter");

            options.elem.append('<div class="eleTree-loadData"><i class="layui-icon layui-icon-loading layui-icon layui-anim layui-anim-rotate layui-anim-loop"></i></div>')


            if (options.data.length === 0) {
                this.ajaxGetData();
            } else {
                this.renderData();
            }
        },
        renderData: function () {
            var options = this.config;
            $(this.config.elem).off();

            laytpl(TPL_ELEM(options, 0)).render(options.data, function (string) {
                options.elem.html(string).children().show();
            });

            if (!options.lazy) {
                if (!options.renderAfterExpand || options.defaultExpandAll || options.defaultExpandedKeys.length > 0 || options.defaultCheckedKeys.length > 0) {
                    this.expandAll(options.data, [], 1);
                }
            }

            this.eleTreeEvent();
            this.checkboxRender();
            this.checkboxEvent();
            this.defaultChecked();
            this.nodeEvent();
            this.rightClickMenu();
            if (!options.checkStrictly) {
                this.checkboxInit();
            }
        },
        ajaxGetData: function () {
            var options = this.config;
            var _self = this;
            if (!options.url) {
                laytpl(TPL_NoText()).render(options, function (string) {
                    options.elem.html(string);
                });
                return;
            }
            var data = $.extend({}, options.where);
            if (options.contentType && options.contentType.indexOf("application/json") == 0) {
                data = JSON.stringify(data);
            }

            $.ajax({
                type: options.method || 'get'
                , url: options.url
                , contentType: options.contentType
                , data: data
                , dataType: 'json'
                , headers: options.headers || {}
                , success: function (res) {
                    if (res[options.response.statusName] != options.response.statusCode || !res[options.response.dataName]) {
                        hint.error("Check format");
                        typeof options.done === 'function' && options.done(res);
                        return;
                    }
                    options.data = res[options.response.dataName];
                    _self.renderData();
                    typeof options.done === 'function' && options.done(res);
                }
            });
        },
        reload: function (options) {
            var _self = this;
            if (this.config.data && this.config.data.constructor === Array) this.config.data = [];
            this.config = $.extend({}, this.config, options);

            return eleTree.render(this.config)
        },

        eleTreeEvent: function () {
            var _self = this;
            var options = this.config;

            var expandOnClickNode = options.expandOnClickNode ? ".eleTree-node-content" : ".eleTree-node-content>.eleTree-node-content-icon";
            options.elem.on("click", expandOnClickNode, function (e) {
                e.stopPropagation();
                var eleTreeNodeContent = $(this).parent(".eleTree-node").length === 0 ? $(this).parent(".eleTree-node-content") : $(this);
                var eleNode = eleTreeNodeContent.parent(".eleTree-node");
                var sibNode = eleTreeNodeContent.siblings(".eleTree-node-group");
                var el = eleTreeNodeContent.children(".eleTree-node-content-icon").children(".layui-icon");


                if (_self.prevClickEle) _self.prevClickEle.removeClass("eleTree-node-content-active");
                if (options.highlightCurrent) eleTreeNodeContent.addClass("eleTree-node-content-active");
                _self.prevClickEle = eleTreeNodeContent;


                if (el.hasClass("icon-rotate")) {

                    sibNode.children(".eleTree-node:not(.eleTree-search-hide)").hide("fast");
                    el.removeClass("icon-rotate");
                    return;
                }

                if (sibNode.children(".eleTree-node").length === 0) {
                    var floor = Number(eleNode.attr("eletree-floor")) + 1;


                    var selectParentsFn = function () {
                        if (!options.checkStrictly) {
                            var eleNode1 = sibNode.children(".eleTree-node").eq(0);
                            if (eleNode1.length !== 0) {
                                var siblingNode1 = eleNode1.siblings(".eleTree-node");
                                var item1 = eleNode1.children(".eleTree-node-content").children(".eleTree-hideen").get(0);
                                _self.selectParents(item1, eleNode1, siblingNode1);
                            }
                        }
                    }

                    var data = _self.reInitData(eleNode);
                    var d = data.currentData;

                    if (options.lazy && el.hasClass("lazy-icon")) {
                        el.removeClass("layui-icon-triangle-r").addClass("layui-icon-loading layui-anim layui-anim-rotate layui-anim-loop");
                        options.load(d, function (getData) {

                            if (d[options.request.children]) {
                                d[options.request.children] = d[options.request.children].concat(getData);
                            } else {
                                d[options.request.children] = getData;
                            }
                            var eletreeStatus = eleTreeNodeContent.children("input.eleTree-hideen").attr("eletree-status");
                            if (d[options.request.children] && d[options.request.children].length > 0) {

                                laytpl(TPL_ELEM(options, floor, eletreeStatus)).render(getData, function (string) {
                                    sibNode.append(string).children().show("fast");
                                });
                            } else {
                                el.css("color", "transparent").addClass("leaf-icon");
                            }
                            el.removeClass("lazy-icon layui-icon-loading layui-anim layui-anim-rotate layui-anim-loop").addClass("layui-icon-triangle-r icon-rotate");


                            selectParentsFn();
                            _self.checkboxRender();
                        })
                    } else {
                        var eletreeStatus = eleTreeNodeContent.children("input.eleTree-hideen").attr("eletree-status");
                        d[options.request.children] && d[options.request.children].length > 0 && laytpl(TPL_ELEM(options, floor, eletreeStatus)).render(d[options.request.children], function (string) {
                            sibNode.append(string);
                        });

                        selectParentsFn();
                        _self.checkboxRender();
                    }
                }

                sibNode.children(".eleTree-node:not(.eleTree-search-hide)").show("fast");
                el.addClass("icon-rotate");

                if (options.accordion) {
                    var node = eleTreeNodeContent.parent(".eleTree-node").siblings(".eleTree-node");
                    node.children(".eleTree-node-group").children(".eleTree-node:not(.eleTree-search-hide)").hide("fast");
                    node.children(".eleTree-node-content").children(".eleTree-node-content-icon").children(".layui-icon").removeClass("icon-rotate");
                }
            })
        },

        checkboxEvent: function () {
            var options = this.config;
            var _self = this;
            var checkOnClickNode = options.checkOnClickNode ? ".eleTree-node-content" : ".eleTree-checkbox";

            options.elem.on("click", checkOnClickNode, function (e) {
                e.stopPropagation();
                var eleTreeNodeContent = $(this).parent(".eleTree-node").length === 0 ? $(this).parent(".eleTree-node-content") : $(this);
                var checkbox = eleTreeNodeContent.children(".eleTree-checkbox");
                if (checkbox.hasClass("eleTree-checkbox-disabled")) return;

                var node = eleTreeNodeContent.parent(".eleTree-node");
                // var d=_self.reInitData(node).currentData;

                var inp = checkbox.siblings(".eleTree-hideen").get(0);
                var childNode = eleTreeNodeContent.siblings(".eleTree-node-group").find("input[name='eleTree-node']");


                if (_self.prevClickEle) _self.prevClickEle.removeClass("eleTree-node-content-active");
                if (options.highlightCurrent) eleTreeNodeContent.addClass("eleTree-node-content-active");
                _self.prevClickEle = eleTreeNodeContent;

                if (!inp) return;

                if (inp.checked) {

                    $(inp).prop("checked", false).attr("eleTree-status", "0");

                    if (!options.checkStrictly) {
                        childNode.prop("checked", false);
                        childNode.attr("eleTree-status", "0");
                    }

                } else {

                    $(inp).prop("checked", true).attr("eleTree-status", "1");

                    if (!options.checkStrictly) {
                        childNode.prop("checked", true).attr("eleTree-status", "1");
                    }
                }

                var eleNode = eleTreeNodeContent.parent(".eleTree-node");

                if (!options.checkStrictly) {
                    var siblingNode = eleNode.siblings(".eleTree-node");

                    _self.selectParents(inp, eleNode, siblingNode);
                }

                _self.checkboxRender();

                layui.event.call(inp, MOD_NAME, 'nodeChecked(' + _self.filter + ')', {
                    node: eleNode,
                    data: _self.reInitData(eleNode),
                    isChecked: inp.checked
                });
            })
        },

        checkboxInit: function () {
            var options = this.config;
            var _self = this;
            options.elem.find("input[data-checked]").each(function (index, item) {
                var checkboxEl = $(item).siblings(".eleTree-checkbox");
                var childNode = checkboxEl.parent(".eleTree-node-content").siblings(".eleTree-node-group").find("input[name='eleTree-node']");
                $(item).prop("checked", "checked").attr("eleTree-status", "1");
                checkboxEl.addClass("eleTree-checkbox-checked");
                checkboxEl.children("i").addClass("layui-icon-ok").removeClass("eleTree-checkbox-line");
                if (options.checkStrictly) return;
                childNode.prop("checked", "checked").attr("eleTree-status", "1");
                childNode.siblings(".eleTree-checkbox").addClass("eleTree-checkbox-checked");
                childNode.siblings(".eleTree-checkbox").children("i").addClass("layui-icon-ok").removeClass("eleTree-checkbox-line");


                var eleNode = checkboxEl.parent(".eleTree-node-content").parent(".eleTree-node");
                var siblingNode = eleNode.siblings(".eleTree-node");
                _self.selectParents(item, eleNode, siblingNode);
            })
            _self.checkboxRender();
        },

        selectParents: function (inp, eleNode, siblingNode) {

            while (Number(eleNode.attr("eletree-floor")) !== 0) {

                var arr = [];
                arr.push($(inp).attr("eleTree-status"));
                siblingNode.each(function (index, item) {
                    var siblingIsChecked = $(item).children(".eleTree-node-content").children("input[name='eleTree-node']").attr("eleTree-status");
                    arr.push(siblingIsChecked);
                })

                var parentInput = eleNode.parent(".eleTree-node-group").siblings(".eleTree-node-content").children("input[name='eleTree-node']");

                var parentCheckbox = parentInput.siblings(".eleTree-checkbox");

                if (arr.every(function (val) {
                    return val === "1";
                })) {
                    parentInput.prop("checked", true).attr("eleTree-status", "1");
                }

                if (arr.some(function (val) {
                    return val === "0" || val === "2";
                })) {
                    parentInput.attr("eleTree-status", "2");
                }

                if (arr.every(function (val) {
                    return val === "0";
                })) {
                    parentInput.prop("checked", false);
                    parentInput.attr("eleTree-status", "0");
                }

                var parentNode = eleNode.parents("[eletree-floor='" + (Number(eleNode.attr("eletree-floor")) - 1) + "']");
                var parentCheckbox = parentNode.children(".eleTree-node-content").children("input[name='eleTree-node']").get(0);
                var parentSiblingNode = parentNode.siblings(".eleTree-node");
                eleNode = parentNode;
                inp = parentCheckbox;
                siblingNode = parentSiblingNode;
            }
        },

        expandAll: function (data, arr, floor, isMethodsExpandAll) {
            var options = this.config;
            var _self = this;
            data.forEach(function (val, index) {
                arr.push(index);
                if (val[options.request.children] && val[options.request.children].length > 0) {
                    var el = options.elem.children(".eleTree-node").eq(arr[0]).children(".eleTree-node-group");
                    for (var i = 1; i < arr.length; i++) {
                        el = el.children(".eleTree-node").eq(arr[i]).children(".eleTree-node-group");
                    }
                    laytpl(TPL_ELEM(options, floor)).render(val[options.request.children], function (string) {
                        el.append(string);

                        if (options.defaultExpandAll || isMethodsExpandAll) {
                            el.siblings(".eleTree-node-content").children(".eleTree-node-content-icon").children(".layui-icon").addClass("icon-rotate");
                            el.children().show();
                        } else if (options.defaultExpandedKeys.length > 0) {

                            var f = function (eleP) {
                                if (options.autoExpandParent) {
                                    eleP.parents(".eleTree-node").each(function (i, item) {
                                        if ($(item).data(options.request.key)) {
                                            $(item).children(".eleTree-node-group").siblings(".eleTree-node-content").children(".eleTree-node-content-icon").children(".layui-icon").addClass("icon-rotate");
                                            $(item).children(".eleTree-node-group").children().show();
                                        }
                                    })
                                }
                            }

                            var id = el.parent(".eleTree-node").data(options.request.key);
                            if ($.inArray(id, options.defaultExpandedKeys) !== -1) {
                                el.siblings(".eleTree-node-content").children(".eleTree-node-content-icon").children(".layui-icon").addClass("icon-rotate");
                                el.children().show();

                                f(el.parent(".eleTree-node[data-" + options.request.key + "]"));
                            } else {

                                el.children(".eleTree-node").each(function (index, item) {
                                    var id = $(item).data(options.request.key);
                                    if ($.inArray(id, options.defaultExpandedKeys) !== -1) {
                                        f($(item));
                                        return false;
                                    }
                                })
                            }
                        }
                    });
                    floor++;
                    _self.expandAll(val[options.request.children], arr, floor, isMethodsExpandAll);
                    floor--;
                }
                arr.pop();
            })


        },

        checkedOneNode: function (nodeContent) {
            var options = this.config;
            var inp = nodeContent.children("input.eleTree-hideen").get(0);
            $(inp).prop("checked", true).attr("eleTree-status", "1");

            if (options.checkStrictly) return;


            var childNode = nodeContent.siblings(".eleTree-node-group").find("input[name='eleTree-node']");
            childNode.prop("checked", true).attr("eleTree-status", "1");

            var eleNode = nodeContent.parent(".eleTree-node");
            var siblingNode = eleNode.siblings(".eleTree-node");

            this.selectParents(inp, eleNode, siblingNode);
        },

        defaultChecked: function (dataChecked) {
            var options = this.config;
            var _self = this;
            var arr = dataChecked || options.defaultCheckedKeys;
            if (arr.length === 0) {
                return false;
            }
            arr.forEach(function (val, index) {
                var nodeContent = options.elem.find("[data-" + options.request.key + "='" + val + "']").children(".eleTree-node-content");
                nodeContent.length > 0 && _self.checkedOneNode(nodeContent);
            })
            this.checkboxInit();
        },

        checkboxRender: function () {
            var options = this.config;
            options.elem.find(".eleTree-checkbox").remove();
            options.elem.find("input.eleTree-hideen[type=checkbox]").each(function (index, item) {
                if ($(item).hasClass("eleTree-disabled")) {
                    $(item).after('<div class="eleTree-checkbox eleTree-checkbox-disabled"><i class="layui-icon"></i></div>');
                } else {
                    $(item).after('<div class="eleTree-checkbox"><i class="layui-icon"></i></div>');
                }

                var checkbox = $(item).siblings(".eleTree-checkbox");
                if ($(item).attr("eletree-status") === "1") {
                    checkbox.addClass("eleTree-checkbox-checked");
                    checkbox.children("i").addClass("layui-icon-ok").removeClass("eleTree-checkbox-line");
                } else if ($(item).attr("eletree-status") === "0") {
                    checkbox.removeClass("eleTree-checkbox-checked");
                    checkbox.children("i").removeClass("layui-icon-ok eleTree-checkbox-line");
                } else if ($(item).attr("eletree-status") === "2") {
                    checkbox.addClass("eleTree-checkbox-checked");
                    checkbox.children("i").removeClass("layui-icon-ok").addClass("eleTree-checkbox-line");
                }

            })
        },

        reInitData: function (node) {
            var options = this.config;
            var i = node.index();
            var floor = Number(node.attr("eletree-floor"));
            var arr = [];
            while (floor >= 0) {
                arr.push(i);
                floor = floor - 1;
                node = node.parents("[eletree-floor='" + floor + "']");
                i = node.index();
            }
            arr = arr.reverse();
            var oData = this.config.data;

            var parentData = oData[arr[0]];

            var d = oData[arr[0]];
            for (var j = 1; j < arr.length; j++) {
                d = d[options.request.children] ? d[options.request.children][arr[j]] : d;
            }
            for (var k = 1; k < arr.length - 1; k++) {
                parentData = parentData[options.request.children] ? parentData[options.request.children][arr[k]] : parentData;
            }

            return {
                currentData: d,
                parentData: {
                    data: parentData,
                    childIndex: arr[arr.length - 1]
                },
                index: arr
            }
        },

        keySearchToOpera: function (key, callback) {
            var options = this.config;
            var _self = this;

            var fn = function (data) {
                var obj = {
                    i: 0,
                    len: data.length
                }
                for (; obj.i < obj.len; obj.i++) {
                    if (data[obj.i][options.request.key] != key) {
                        if (data[obj.i][options.request.children] && data[obj.i][options.request.children].length > 0) {
                            fn(data[obj.i][options.request.children]);
                        }
                    } else {
                        callback(data, obj);
                    }
                }
            }
            fn(options.data);
        },
        updateKeyChildren: function (key, data) {
            var options = this.config;
            var node = options.elem.find("[data-" + options.request.key + "='" + key + "']");
            var floor = Number(node.attr("eletree-floor")) + 1;
            var _self = this;

            this.keySearchToOpera(key, function (d, obj) {

                d[obj.i][options.request.children] = data;

                node.length !== 0 && laytpl(TPL_ELEM(options, floor)).render(data, function (string) {
                    $(node).children(".eleTree-node-group").empty().append(string);
                    options.defaultExpandAll && $(node).children(".eleTree-node-group").children().show();
                });
                _self.unCheckNodes(true);
                _self.defaultChecked();
            });
        },
        updateKeySelf: function (key, data) {
            var options = this.config;
            var node = options.elem.find("[data-" + options.request.key + "='" + key + "']").children(".eleTree-node-content");
            var floor = Number(node.attr("eletree-floor")) + 1;
            data[options.request.name] && node.children(".eleTree-node-content-label").text(data[options.request.name]);
            data[options.request.disabled] && node.children(".eleTree-hideen").addClass("eleTree-disabled")
                .siblings(".eleTree-checkbox").addClass("eleTree-checkbox-disabled");

            var getData = this.keySearchToOpera(key, function (d, obj) {
                data[options.request.key] = d[obj.i][options.request.key];
                data[options.request.children] = d[obj.i][options.request.children];
                d[obj.i] = $.extend({}, d[obj.i], data);
            });
        },
        remove: function (key) {
            var options = this.config;
            var node = options.elem.find("[data-" + options.request.key + "='" + key + "']");
            var pElem = node.parent(".eleTree-node-group");

            this.keySearchToOpera(key, function (data, obj) {
                data.splice(obj.i, 1);
                obj.i--;
                obj.len--;

                node.length !== 0 && options.elem.find("[data-" + options.request.key + "='" + key + "']").remove();
                if (pElem.children(".eleTree-node").length === 0) {
                    pElem.siblings(".eleTree-node-content").children(".eleTree-node-content-icon").children(".layui-icon").css("color", "transparent");
                }
            });
            this.unCheckNodes(true);
            this.defaultChecked();
            this.checkboxInit();
        },
        append: function (key, data) {
            var options = this.config;

            if (typeof key === "object" && key !== null) {
                data = key;
                key = null;
            }
            if (key === null || key === "") {
                options.data.push(data);
                laytpl(TPL_ELEM(options, 0, "0")).render([data], function (string) {
                    $(options.elem).append(string);
                    $(options.elem).children(".eleTree-node:last").show();
                });
                this.checkboxRender();
                return;
            }

            var node = options.elem.find("[data-" + options.request.key + "='" + key + "']");
            var floor = Number(node.attr("eletree-floor")) + 1;

            this.keySearchToOpera(key, function (d, obj) {
                if (d[obj.i][options.request.children]) {
                    d[obj.i][options.request.children].push(data);
                } else {
                    d[obj.i][options.request.children] = [data];
                }
                var arr = d[obj.i][options.request.children];
                var icon = node.children(".eleTree-node-content").find(".eleTree-node-content-icon .layui-icon");

                if (arr.length === 1) {
                    icon.removeAttr("style");
                }

                if (!icon.hasClass("icon-rotate")) {
                    var expandOnClickNode = options.expandOnClickNode ? node.children(".eleTree-node-content") : node.children(".eleTree-node-content").children(".eleTree-node-content-icon");
                    expandOnClickNode.trigger("click");
                }

                var isExist = false;
                node.children(".eleTree-node-group").children(".eleTree-node").each(function (index, item) {
                    if (data[options.request.key] == $(item).data(options.request.key)) {
                        isExist = true;
                    }
                })
                if (!isExist) {
                    var len = arr.length;
                    var eletreeStatus = node.children(".eleTree-node-content").children("input.eleTree-hideen").attr("eletree-status");
                    eletreeStatus = eletreeStatus === "2" ? "0" : eletreeStatus;
                    node.length !== 0 && laytpl(TPL_ELEM(options, floor, eletreeStatus)).render([arr[len - 1]], function (string) {
                        node.children(".eleTree-node-group").append(string).children().show();
                    });
                }

            });
            this.checkboxRender();
        },
        insertBefore: function (key, data) {
            var options = this.config;
            var node = options.elem.find("[data-" + options.request.key + "='" + key + "']");
            var floor = Number(node.attr("eletree-floor"));

            this.keySearchToOpera(key, function (d, obj) {
                d.splice(obj.i, 0, data);
                obj.i++;
                obj.len++;
                var eletreeStatus = node.parent(".eleTree-node-group").length === 0 ? "0" : node.parent(".eleTree-node-group").parent(".eleTree-node")
                    .children(".eleTree-node-content").children("input.eleTree-hideen").attr("eletree-status");
                eletreeStatus = eletreeStatus === "2" ? "0" : eletreeStatus;
                node.length !== 0 && laytpl(TPL_ELEM(options, floor, eletreeStatus)).render([data], function (string) {
                    node.before(string).prev(".eleTree-node").show();
                });
            });
            this.checkboxRender();
        },
        insertAfter: function (key, data) {
            var options = this.config;
            var node = options.elem.find("[data-" + options.request.key + "='" + key + "']");
            var floor = Number(node.attr("eletree-floor"));

            this.keySearchToOpera(key, function (d, obj) {
                d.splice(obj.i + 1, 0, data);
                obj.i++;
                obj.len++;
                var eletreeStatus = node.parent(".eleTree-node-group").length === 0 ? "0" : node.parent(".eleTree-node-group").parent(".eleTree-node")
                    .children(".eleTree-node-content").children("input.eleTree-hideen").attr("eletree-status");
                eletreeStatus = eletreeStatus === "2" ? "0" : eletreeStatus;
                node.length !== 0 && laytpl(TPL_ELEM(options, floor, eletreeStatus)).render([data], function (string) {
                    $(node).after(string).next(".eleTree-node").show();
                });
            });
            this.checkboxRender();

        },
        getChecked: function (leafOnly, includeHalfChecked) {
            var options = this.config
                , el
                , arr = [];
            leafOnly = leafOnly || false;
            includeHalfChecked = includeHalfChecked || false;
            if (leafOnly) {
                el = options.elem.find(".layui-icon.leaf-icon").parent(".eleTree-node-content-icon")
                    .siblings("input.eleTree-hideen[eletree-status='1']");
            } else if (includeHalfChecked) {
                el = options.elem.find("input.eleTree-hideen[eletree-status='1'],input.eleTree-hideen[eletree-status='2']");
            } else {
                el = options.elem.find("input.eleTree-hideen[eletree-status='1']");
            }
            el.each(function (index, item) {
                var obj = {};
                var id = $(item).parent(".eleTree-node-content").parent(".eleTree-node").data(options.request.key);
                var label = $(item).siblings(".eleTree-node-content-label").text();
                obj[options.request.key] = id;
                obj[options.request.name] = label;
                obj.elem = item;
                obj.type = $(item).parent(".eleTree-node-content").attr('type');
                obj.othis = $(item).siblings(".eleTree-checkbox").get(0);
                arr.push(obj);
            })
            return arr;
        },
        setChecked: function (arr, isReset) {
            var options = this.config;
            isReset = isReset || false;
            if (isReset) {
                this.unCheckNodes();
                options.defaultCheckedKeys = $.extend([], arr);
            } else {
                this.unCheckNodes(true);
                arr.forEach(function (val) {
                    if ($.inArray(val, options.defaultCheckedKeys) === -1) {
                        options.defaultCheckedKeys.push(val);
                    }
                })
            }
            this.defaultChecked();
        },
        unCheckNodes: function (_internal) {
            _internal = _internal || false;
            var options = this.config;
            options.elem.find("input.eleTree-hideen[eletree-status='1'],input.eleTree-hideen[eletree-status='2']").each(function (index, item) {
                $(item).attr("eletree-status", "0").prop("checked", false);

                if (!_internal) {
                    $(item).removeAttr("data-checked");
                }
            });
            this.checkboxRender();
        },
        unCheckArrNodes: function (arr) {
            var options = this.config;
            var dataChecked = [];
            options.elem.find(".eleTree-hideen[eletree-status='1']").each(function (index, item) {
                var id = $(item).parent(".eleTree-node-content").parent(".eleTree-node").data(options.request.key);

                if (arr.some(function (val) {
                    return val == id;
                })) {

                    $(item).removeAttr("data-checked");
                    return;
                }
                dataChecked.push(id);
            })


            for (var j = 0; j < options.defaultCheckedKeys.length; j++) {
                if (!dataChecked.some(function (val) {
                    return val == options.defaultCheckedKeys[j];
                })) {
                    options.defaultCheckedKeys.splice(j, 1);
                    j--;
                }
            }
            this.unCheckNodes(true);
            this.defaultChecked(dataChecked);
        },
        unExpandAll: function () {
            var options = this.config;
            options.elem.find(".layui-icon.icon-rotate").removeClass("icon-rotate")
                .parent(".eleTree-node-content-icon").parent(".eleTree-node-content")
                .siblings(".eleTree-node-group").children(".eleTree-node").hide();
        },

        nodeEvent: function () {
            var _self = this;
            var options = this.config;

            options.elem.on("click", ".eleTree-node-content", function (e) {
                var eleNode = $(this).parent(".eleTree-node");
                $("#tree-menu").hide().remove();
                layui.event.call(eleNode, MOD_NAME, 'nodeClick(' + _self.filter + ')', {
                    node: eleNode,
                    data: _self.reInitData(eleNode),
                    event: e
                });
            })

            options.elem.on("contextmenu", ".eleTree-node-content", function (e) {
                var eleNode = $(this).parent(".eleTree-node");
                layui.event.call(eleNode, MOD_NAME, 'nodeContextmenu(' + _self.filter + ')', {
                    node: eleNode,
                    data: _self.reInitData(eleNode),
                    event: e
                });
            })

            options.draggable && options.elem.on("mousedown", ".eleTree-node-content", function (e) {
                var time = 0;
                var eleNode = $(this).parent(".eleTree-node");
                var eleFloor = Number(eleNode.attr("eletree-floor"));
                var groupNode = eleNode.parent(".eleTree-node-group");

                e.stopPropagation();
                options.elem.css("user-select", "none");
                var cloneNode = eleNode.clone(true);
                var temNode = eleNode.clone(true);

                var x = e.clientX - options.elem.offset().left;
                var y = e.clientY - options.elem.offset().top;
                options.elem.append(cloneNode);
                cloneNode.css({
                    "display": "none",
                    "opacity": 0.7,
                    "position": "absolute",
                    "background-color": "#f5f5f5",
                    "width": "100%"
                })

                var currentData = _self.reInitData(eleNode);

                var isStop = false;

                $(document).on("mousemove", function (e) {

                    time++;
                    if (time > 2) {
                        var xx = e.clientX - options.elem.offset().left + 10;
                        var yy = e.clientY - options.elem.offset().top + $(document).scrollTop() - 5;   // 加上浏览器滚动高度

                        cloneNode.css({
                            display: "block",
                            left: xx + "px",
                            top: yy + "px"
                        })
                    }
                }).on("mouseup", function (e) {
                    $(document).off("mousemove").off("mouseup");
                    var target = $(e.target).parents(".eleTree-node").eq(0);
                    cloneNode.remove();
                    options.elem.css("user-select", "auto");



                    var isCurrentOuterMost = eleNode.parent().get(0).isEqualNode(options.elem.get(0))

                    var isTargetOuterMost = $(e.target).get(0).isEqualNode(options.elem.get(0))
                    if (isTargetOuterMost) {
                        target = options.elem;
                    }

                    if (target.parents(options.elem).length === 0 && !isTargetOuterMost) {
                        return;
                    }

                    if (target.get(0).isEqualNode(eleNode.get(0))) {
                        return;
                    }

                    var tFloor = target.attr("eletree-floor");
                    var isInChild = false;
                    eleNode.find("[eletree-floor='" + tFloor + "']").each(function () {
                        if (this.isEqualNode(target.get(0))) {
                            isInChild = true;
                        }
                    })
                    if (isInChild) {
                        return;
                    }

                    var targetData = _self.reInitData(target);
                    layui.event.call(target, MOD_NAME, 'nodeDrag(' + _self.filter + ')', {
                        current: {
                            node: eleNode,
                            data: currentData
                        },
                        target: {
                            node: target,
                            data: targetData
                        },
                        stop: function () {
                            isStop = true;
                        }
                    });

                    if (isStop) {
                        return false;
                    }


                    var currList = currentData.parentData.data[options.request.children]
                    var currIndex = currentData.parentData.childIndex
                    var currData = currentData.currentData;
                    var tarData = targetData.currentData;

                    isCurrentOuterMost ? options.data.splice(currIndex, 1) : currList.splice(currIndex, 1)

                    isTargetOuterMost ? options.data.push(currData) : (function () {
                        !tarData[options.request.children] ? tarData[options.request.children] = [] : "";
                        tarData[options.request.children].push(currData);
                    })()

                    /
                    eleNode.remove();
                    var floor = null;

                    if (isTargetOuterMost) {
                        target.append(temNode);
                        floor = 0;
                    } else {
                        target.children(".eleTree-node-group").append(temNode);
                        floor = Number(target.attr("eletree-floor")) + 1;
                    }

                    temNode.attr("eletree-floor", String(floor));
                    temNode.children(".eleTree-node-content").css("padding-left", floor * options.indent + "px");

                    var countFloor = eleFloor - floor;
                    temNode.find(".eleTree-node").each(function (index, item) {
                        var f = Number($(item).attr("eletree-floor")) - countFloor;
                        $(item).attr("eletree-floor", String(f));
                        $(item).children(".eleTree-node-content").css("padding-left", f * options.indent + "px");
                    })

                    var leaf = groupNode.children(".eleTree-node").length === 0;
                    leaf && groupNode.siblings(".eleTree-node-content")
                        .children(".eleTree-node-content-icon").children(".layui-icon")
                        .removeClass("icon-rotate").css("color", "transparent");

                    var cLeaf = target.children(".eleTree-node-group").children(".eleTree-node").length === 0;
                    !cLeaf && target.children(".eleTree-node-content")
                        .children(".eleTree-node-content-icon").children(".layui-icon")
                        .addClass("icon-rotate").removeAttr("style");

                    _self.unCheckNodes(true);
                    _self.defaultChecked();
                    _self.checkboxInit();
                })
            })
        },
        rightClickMenu: function () {
            var _self = this;
            var options = this.config;
            if (options.contextmenuList.length <= 0) {
                return;
            }
            $(document).on("click", function () {
                $("#tree-menu").hide().remove();
            });

            var customizeMenu = [];
            var internalMenu = ["copy", "add", "add.async", "insertBefore", "insertAfter", "append", "edit", "edit.async", "remove", "remove.async"];  // 系统自带的
            var customizeStr = '';
            options.contextmenuList.forEach(function (val) {
                if ($.inArray(val, internalMenu) === -1) {
                    customizeMenu.push(val);
                    customizeStr += '<li class="' + (val.eventName || val) + '"><a href="javascript:;">' + (val.text || val) + '</a></li>';
                }
            })
            var menuStr = ['<ul id="tree-menu">'
                , $.inArray("copy", options.contextmenuList) !== -1 ? '<li class="copy"><a href="javascript:;">复制</a></li>' : ''
                , ($.inArray("add", options.contextmenuList) !== -1 || $.inArray("add.async", options.contextmenuList) !== -1) ? '<li class="add"><a href="javascript:;">新增</a></li>' +
                    '<li class="insertBefore"><a href="javascript:;">Before inserting a node</a></li>' +
                    '<li class="insertAfter"><a href="javascript:;">After inserting the node</a></li>' +
                    '<li class="append"><a href="javascript:;">Insert child node</a></li>' : ""
                , ($.inArray("edit", options.contextmenuList) !== -1 || $.inArray("edit.async", options.contextmenuList) !== -1) ? '<li class="edit"><a href="javascript:;">修改</a></li>' : ''
                , ($.inArray("remove", options.contextmenuList) !== -1 || $.inArray("remove.async", options.contextmenuList) !== -1) ? '<li class="remove"><a href="javascript:;">删除</a></li>' : ''
                , customizeStr
                , '</ul>'].join("");
            this.treeMenu = $(menuStr);
            options.elem.off("contextmenu").on("contextmenu", ".eleTree-node-content", function (e) {
                var that = this;
                e.stopPropagation();
                e.preventDefault();

                if (_self.prevClickEle) _self.prevClickEle.removeClass("eleTree-node-content-active");
                $(this).addClass("eleTree-node-content-active");
                var eleNode = $(this).parent(".eleTree-node");
                var nodeData = _self.reInitData(eleNode);


                $(document.body).after(_self.treeMenu);
                $("#tree-menu").find("li.append,li.insertAfter,li.insertBefore").hide();
                $("#tree-menu").find(":not(li.append,li.insertAfter,li.insertBefore)").show();
                $("#tree-menu").css({
                    left: e.clientX + $(document).scrollLeft(),
                    top: e.clientY + $(document).scrollTop()
                }).show();

                $("#tree-menu li.copy").off().on("click", function () {
                    var el = $(that).children(".eleTree-node-content-label").get(0);
                    var selection = window.getSelection();
                    var range = document.createRange();
                    range.selectNodeContents(el);
                    selection.removeAllRanges();
                    selection.addRange(range);
                    document.execCommand('Copy', 'false', null);
                    selection.removeAllRanges();
                });

                $("#tree-menu li.add").off().on("click", function (e) {
                    e.stopPropagation();
                    $(this).hide().siblings("li:not(.append,.insertAfter,.insertBefore)").hide();
                    $(this).siblings(".append,li.insertAfter,li.insertBefore").show();
                })

                var obj = {};
                obj[options.request.key] = Date.now();
                obj[options.request.name] = "Unnamed" + _self.nameIndex;
                if (options.lazy) {
                    obj[options.request.isLeaf] = true;
                }

                var arr = ["Append", "InsertBefore", "InsertAfter"];
                arr.forEach(function (val) {
                    var s = val[0].toLocaleLowerCase() + val.slice(1, val.length);
                    $("#tree-menu li." + s).off().on("click", function (e) {
                        var node = $(that).parent(".eleTree-node");
                        var key = node.data(options.request.key);
                        var isStop = false;
                        var s = val[0].toLocaleLowerCase() + val.slice(1, val.length);

                        _self[s](key, obj);
                        var nodeArr = [];
                        node.children(".eleTree-node-group").children(".eleTree-node").each(function (i, itemNode) {
                            nodeArr.push(itemNode);
                        })
                        node.siblings(".eleTree-node").each(function (i, itemNode) {
                            nodeArr.push(itemNode);
                        })
                        $.each(nodeArr, function (i, itemNode) {
                            if (obj[options.request.key] === $(itemNode).data(options.request.key)) {
                                var label = $(itemNode).children(".eleTree-node-content").children(".eleTree-node-content-label").hide();
                                var text = label.text();
                                var inp = "<input type='text' value='" + obj[options.request.name] + "' class='eleTree-node-content-input' />";
                                label.after(inp);

                                label.siblings(".eleTree-node-content-input").focus().off().on("blur", function () {
                                    var v = $(this).val();
                                    obj[options.request.name] = v;
                                    var inpThis = this;

                                    layui.event.call(node, MOD_NAME, 'node' + val + '(' + _self.filter + ')', {
                                        node: node,
                                        data: nodeData.currentData,
                                        newData: obj,

                                        setData: function (o) {
                                            // obj[options.request.key]=Date.now();
                                            obj[options.request.name] = v;
                                            if (options.lazy) {
                                                obj[options.request.isLeaf] = true;
                                            }
                                            var newObj = $.extend({}, obj, o);
                                            this.newData = newObj;

                                            var d = _self.reInitData($(itemNode)).currentData;
                                            d[options.request.name] = newObj[options.request.name];
                                            d[options.request.key] = newObj[options.request.key];

                                            $(inpThis).siblings(".eleTree-node-content-label").text(newObj[options.request.name]).show();
                                            $(itemNode).attr("data-" + options.request.key, newObj[options.request.key]);  // 改变页面上面的显示的key，之后可以获取dom
                                            $(itemNode).data(options.request.key, newObj[options.request.key]);          // 改变data数据，之后可以通过data获取key
                                            $(inpThis).remove();

                                            _self.nameIndex++;
                                            isStop = true;
                                        },

                                        stop: function () {
                                            isStop = true;
                                            this.newData = {};
                                            _self.remove(obj[options.request.key]);
                                        }
                                    });


                                    if ($.inArray("add.async", options.contextmenuList) === -1) {
                                        if (isStop) return;

                                        _self.reInitData($(itemNode)).currentData[options.request.name] = v;

                                        $(this).siblings(".eleTree-node-content-label").text(v).show();
                                        $(this).remove();

                                        _self.nameIndex++;
                                    }
                                }).on("mousedown", function (e) {

                                    e.stopPropagation();
                                }).on("click", function (e) {
                                    e.stopPropagation();
                                })
                            }
                        })
                    })
                })


                $("#tree-menu li.edit").off().on("click", function (e) {
                    e.stopPropagation();
                    $("#tree-menu").hide().remove();
                    var node = $(that).parent(".eleTree-node");
                    var key = node.data(options.request.key);
                    var label = $(that).children(".eleTree-node-content-label").hide();
                    var text = label.text();
                    var inp = "<input type='text' value='" + text + "' class='eleTree-node-content-input' />";
                    label.after(inp);
                    label.siblings(".eleTree-node-content-input").focus().select().off().on("blur", function () {
                        var val = $(this).val();
                        var isStop = false;
                        var inpThis = this;
                        layui.event.call(node, MOD_NAME, 'nodeEdit(' + _self.filter + ')', {
                            node: node,
                            value: val,
                            data: nodeData.currentData,

                            stop: function () {
                                isStop = true;
                                $(inpThis).siblings(".eleTree-node-content-label").show();
                                $(inpThis).remove();
                            },
                            async: function () {
                                if (isStop) return;

                                _self.reInitData(eleNode).currentData[options.request.name] = val;
                                /
                                $(inpThis).siblings(".eleTree-node-content-label").text(val).show();
                                $(inpThis).remove();
                            }
                        });

                        if ($.inArray("edit.async", options.contextmenuList) === -1) {
                            if (isStop) return;

                            _self.reInitData(eleNode).currentData[options.request.name] = val;

                            $(this).siblings(".eleTree-node-content-label").text(val).show();
                            $(this).remove();
                        }

                    }).on("mousedown", function (e) {

                        e.stopPropagation();
                    })
                })

                $("#tree-menu li.remove").off().on("click", function (e) {
                    var node = $(that).parent(".eleTree-node");
                    var key = node.data(options.request.key);
                    var isStop = false;
                    layui.event.call(node, MOD_NAME, 'nodeRemove(' + _self.filter + ')', {
                        node: node,
                        data: nodeData.currentData,

                        stop: function () {
                            isStop = true;
                            return this;
                        },
                        async: function () {
                            if (isStop) return;
                            _self.remove(key);
                            return this;
                        }
                    });

                    if ($.inArray("remove.async", options.contextmenuList) === -1) {
                        if (isStop) return;
                        _self.remove(key);
                    }

                })


                customizeMenu.forEach(function (val) {
                    var text = val.eventName || val;
                    $("#tree-menu li." + text).off().on("click", function () {
                        var node = $(that).parent(".eleTree-node");
                        var isStop = false;
                        layui.event.call(node, MOD_NAME, 'node' + text.replace(text.charAt(0), text.charAt(0).toUpperCase()) + '(' + _self.filter + ')', {
                            node: node,
                            data: nodeData.currentData,
                        });
                    });
                })

                _self.prevClickEle = $(this);
            })
        },
        search: function (value) {
            var options = this.config;
            if (!options.searchNodeMethod || typeof options.searchNodeMethod !== "function") {
                return;
            }
            var data = options.data;

            var traverse = function (data) {
                data.forEach(function (val, index) {

                    val.visible = options.searchNodeMethod(value, val);
                    if (val[options.request.children] && val[options.request.children].length > 0) {
                        traverse(val[options.request.children]);
                    }

                    if (!val.visible) {
                        var childSomeShow = false;
                        if (val[options.request.children] && val[options.request.children].length > 0) {
                            childSomeShow = val[options.request.children].some(function (v, i) {
                                return v.visible;
                            })
                        }
                        val.visible = childSomeShow;
                    }

                    var el = options.elem.find("[data-" + options.request.key + "='" + val[options.request.key] + "']");
                    if (val.visible) {
                        el.removeClass("eleTree-search-hide");

                        var parentEl = el.parent(".eleTree-node-group").parent(".eleTree-node");
                        var isParentOpen = parentEl.children(".eleTree-node-content").children(".eleTree-node-content-icon").children(".layui-icon.layui-icon-triangle-r").hasClass("icon-rotate")
                        if ((parentEl.length > 0 && isParentOpen) || parentEl.length === 0) {
                            el.show();
                        }
                    } else {
                        el.hide().addClass("eleTree-search-hide");
                    }

                    // if(val[options.request.children] && val[options.request.children].length>0){
                    //     val[options.request.children].forEach(function(v,i) {
                    //         delete v.visible;
                    //     })
                    // }
                })
            }
            traverse(data);

            var arr = [];
            data.forEach(function (val) {
                arr.push(val.visible);
                // delete val.visible;
            })
            var isNotext = options.elem.children(".eleTree-noText");

            if (arr.every(function (v) {
                return v === false;
            })) {
                if (isNotext.length === 0) {
                    laytpl(TPL_NoText()).render(options, function (string) {
                        options.elem.append(string);
                    });
                }
            } else {
                isNotext.remove();
            }
        }
    }

    exports(MOD_NAME, eleTree);
})
