


///////////////////////////////////////////////Footable Responsive Table JS Plugin Start////////////////////////////////////////////////////////
(function ($, w, undefined) {
w.footable = {
options: {
delay: 100, // The number of millseconds to wait before triggering the react event
breakpoints: { // The different screen resolution breakpoints
phone: 480,
tablet: 768
},
parsers: {  // The default parser to parse the value out of a cell (values are used in building up row detail)
alpha: function (cell) {
return $(cell).data('value') || $.trim($(cell).text());
},
numeric: function (cell) {
var val = $(cell).data('value') || $(cell).text().replace(/[^0-9.\-]/g, '');
val = parseFloat(val);
if (isNaN(val)) val = 0;
return val;
}
},
addRowToggle: true,
calculateWidthOverride: null,
toggleSelector: ' > tbody > tr:not(.footable-row-detail)', //the selector to show/hide the detail row
columnDataSelector: '> thead > tr:last-child > th, > thead > tr:last-child > td', //the selector used to find the column data in the thead
detailSeparator: ':', //the separator character used when building up the detail row
toggleHTMLElement: '<span />', // override this if you want to insert a click target rather than use a background image.
createGroupedDetail: function (data) {
var groups = { '_none': { 'name': null, 'data': [] } };
for (var i = 0; i < data.length; i++) {
var groupid = data[i].group;
if (groupid !== null) {
if (!(groupid in groups))
groups[groupid] = { 'name': data[i].groupName || data[i].group, 'data': [] };

groups[groupid].data.push(data[i]);
} else {
groups._none.data.push(data[i]);
}
}
return groups;
},
createDetail: function (element, data, createGroupedDetail, separatorChar, classes) {
/// <summary>This function is used by FooTable to generate the detail view seen when expanding a collapsed row.</summary>
/// <param name="element">This is the div that contains all the detail row information, anything could be added to it.</param>
/// <param name="data">
///  This is an array of objects containing the cell information for the current row.
///  These objects look like the below:
///    obj = {
///      'name': String, // The name of the column
///      'value': Object, // The value parsed from the cell using the parsers. This could be a string, a number or whatever the parser outputs.
///      'display': String, // This is the actual HTML from the cell, so if you have images etc you want moved this is the one to use and is the default value used.
///      'group': String, // This is the identifier used in the data-group attribute of the column.
///      'groupName': String // This is the actual name of the group the column belongs to.
///    }
/// </param>
/// <param name="createGroupedDetail">The grouping function to group the data</param>
/// <param name="separatorChar">The separator charactor used</param>
/// <param name="classes">The array of class names used to build up the detail row</param>

var groups = createGroupedDetail(data);
for (var group in groups) {
if (groups[group].data.length === 0) continue;
if (group !== '_none') element.append('<div class="' + classes.detailInnerGroup + '">' + groups[group].name + '</div>');

for (var j = 0; j < groups[group].data.length; j++) {
var separator = (groups[group].data[j].name) ? separatorChar : '';
element.append($('<div></div>').addClass(classes.detailInnerRow).append($('<div></div>').addClass(classes.detailInnerName)
.append(groups[group].data[j].name + separator)).append($('<div></div>').addClass(classes.detailInnerValue)
.attr('data-bind-value', groups[group].data[j].bindName).append(groups[group].data[j].display)));
}
}
},
classes: {
main: 'footable',
loading: 'footable-loading',
loaded: 'footable-loaded',
toggle: 'footable-toggle',
disabled: 'footable-disabled',
detail: 'footable-row-detail',
detailCell: 'footable-row-detail-cell',
detailInner: 'footable-row-detail-inner',
detailInnerRow: 'footable-row-detail-row',
detailInnerGroup: 'footable-row-detail-group',
detailInnerName: 'footable-row-detail-name',
detailInnerValue: 'footable-row-detail-value',
detailShow: 'footable-detail-show'
},
triggers: {
initialize: 'footable_initialize',                      //trigger this event to force FooTable to reinitialize
resize: 'footable_resize',                              //trigger this event to force FooTable to resize
redraw: 'footable_redraw',                              //trigger this event to force FooTable to redraw
toggleRow: 'footable_toggle_row',                       //trigger this event to force FooTable to toggle a row
expandFirstRow: 'footable_expand_first_row',            //trigger this event to force FooTable to expand the first row
expandAll: 'footable_expand_all',                       //trigger this event to force FooTable to expand all rows
collapseAll: 'footable_collapse_all'                    //trigger this event to force FooTable to collapse all rows
},
events: {
alreadyInitialized: 'footable_already_initialized',     //fires when the FooTable has already been initialized
initializing: 'footable_initializing',                  //fires before FooTable starts initializing
initialized: 'footable_initialized',                    //fires after FooTable has finished initializing
resizing: 'footable_resizing',                          //fires before FooTable resizes
resized: 'footable_resized',                            //fires after FooTable has resized
redrawn: 'footable_redrawn',                            //fires after FooTable has redrawn
breakpoint: 'footable_breakpoint',                      //fires inside the resize function, when a breakpoint is hit
columnData: 'footable_column_data',                     //fires when setting up column data. Plugins should use this event to capture their own info about a column
rowDetailUpdating: 'footable_row_detail_updating',      //fires before a detail row is updated
rowDetailUpdated: 'footable_row_detail_updated',        //fires when a detail row is being updated
rowCollapsed: 'footable_row_collapsed',                 //fires when a row is collapsed
rowExpanded: 'footable_row_expanded',                   //fires when a row is expanded
rowRemoved: 'footable_row_removed',                     //fires when a row is removed
reset: 'footable_reset'                                 //fires when FooTable is reset
},
debug: false, // Whether or not to log information to the console.
log: null
},

version: {
major: 0, minor: 5,
toString: function () {
return w.footable.version.major + '.' + w.footable.version.minor;
},
parse: function (str) {
var version = /(\d+)\.?(\d+)?\.?(\d+)?/.exec(str);
return {
major: parseInt(version[1], 10) || 0,
minor: parseInt(version[2], 10) || 0,
patch: parseInt(version[3], 10) || 0
};
}
},

plugins: {
_validate: function (plugin) {
///<summary>Simple validation of the <paramref name="plugin"/> to make sure any members called by FooTable actually exist.</summary>
///<param name="plugin">The object defining the plugin, this should implement a string property called "name" and a function called "init".</param>

if (!$.isFunction(plugin)) {
if (w.footable.options.debug === true) console.error('Validation failed, expected type "function", received type "{0}".', typeof plugin);
return false;
}
var p = new plugin();
if (typeof p['name'] !== 'string') {
if (w.footable.options.debug === true) console.error('Validation failed, plugin does not implement a string property called "name".', p);
return false;
}
if (!$.isFunction(p['init'])) {
if (w.footable.options.debug === true) console.error('Validation failed, plugin "' + p['name'] + '" does not implement a function called "init".', p);
return false;
}
if (w.footable.options.debug === true) console.log('Validation succeeded for plugin "' + p['name'] + '".', p);
return true;
},
registered: [], // An array containing all registered plugins.
register: function (plugin, options) {
///<summary>Registers a <paramref name="plugin"/> and its default <paramref name="options"/> with FooTable.</summary>
///<param name="plugin">The plugin that should implement a string property called "name" and a function called "init".</param>
///<param name="options">The default options to merge with the FooTable's base options.</param>

if (w.footable.plugins._validate(plugin)) {
w.footable.plugins.registered.push(plugin);
if (typeof options === 'object') $.extend(true, w.footable.options, options);
}
},
load: function(instance){
var loaded = [], registered, i;
for(i = 0; i < w.footable.plugins.registered.length; i++){
try {
registered = w.footable.plugins.registered[i];
loaded.push(new registered(instance));
} catch (err) {
if (w.footable.options.debug === true) console.error(err);
}
}
return loaded;
},
init: function (instance) {
///<summary>Loops through all registered plugins and calls the "init" method supplying the current <paramref name="instance"/> of the FooTable as the first parameter.</summary>
///<param name="instance">The current instance of the FooTable that the plugin is being initialized for.</param>

for (var i = 0; i < instance.plugins.length; i++) {
try {
instance.plugins[i]['init'](instance);
} catch (err) {
if (w.footable.options.debug === true) console.error(err);
}
}
}
}
};

var instanceCount = 0;

$.fn.footable = function (options) {
///<summary>The main constructor call to initialize the plugin using the supplied <paramref name="options"/>.</summary>
///<param name="options">
///<para>A JSON object containing user defined options for the plugin to use. Any options not supplied will have a default value assigned.</para>
///<para>Check the documentation or the default options object above for more information on available options.</para>
///</param>

options = options || {};
var o = $.extend(true, {}, w.footable.options, options); //merge user and default options
return this.each(function () {
instanceCount++;
var footable = new Footable(this, o, instanceCount);
$(this).data('footable', footable);
});
};

//helper for using timeouts
function Timer() {
///<summary>Simple timer object created around a timeout.</summary>
var t = this;
t.id = null;
t.busy = false;
t.start = function (code, milliseconds) {
///<summary>Starts the timer and waits the specified amount of <paramref name="milliseconds"/> before executing the supplied <paramref name="code"/>.</summary>
///<param name="code">The code to execute once the timer runs out.</param>
///<param name="milliseconds">The time in milliseconds to wait before executing the supplied <paramref name="code"/>.</param>

if (t.busy) {
return;
}
t.stop();
t.id = setTimeout(function () {
code();
t.id = null;
t.busy = false;
}, milliseconds);
t.busy = true;
};
t.stop = function () {
///<summary>Stops the timer if its runnning and resets it back to its starting state.</summary>

if (t.id !== null) {
clearTimeout(t.id);
t.id = null;
t.busy = false;
}
};
}

function Footable(t, o, id) {
///<summary>Inits a new instance of the plugin.</summary>
///<param name="t">The main table element to apply this plugin to.</param>
///<param name="o">The options supplied to the plugin. Check the defaults object to see all available options.</param>
///<param name="id">The id to assign to this instance of the plugin.</param>

var ft = this;
ft.id = id;
ft.table = t;
ft.options = o;
ft.breakpoints = [];
ft.breakpointNames = '';
ft.columns = {};
ft.plugins = w.footable.plugins.load(ft);

var opt = ft.options,
cls = opt.classes,
evt = opt.events,
trg = opt.triggers,
indexOffset = 0;

// This object simply houses all the timers used in the FooTable.
ft.timers = {
resize: new Timer(),
register: function (name) {
ft.timers[name] = new Timer();
return ft.timers[name];
}
};

ft.init = function () {
var $window = $(w), $table = $(ft.table);

w.footable.plugins.init(ft);

if ($table.hasClass(cls.loaded)) {
//already loaded FooTable for the table, so don't init again
ft.raise(evt.alreadyInitialized);
return;
}

//raise the initializing event
ft.raise(evt.initializing);

$table.addClass(cls.loading);

// Get the column data once for the life time of the plugin
$table.find(opt.columnDataSelector).each(function () {
var data = ft.getColumnData(this);
ft.columns[data.index] = data;
});

// Create a nice friendly array to work with out of the breakpoints object.
for (var name in opt.breakpoints) {
ft.breakpoints.push({ 'name': name, 'width': opt.breakpoints[name] });
ft.breakpointNames += (name + ' ');
}

// Sort the breakpoints so the smallest is checked first
ft.breakpoints.sort(function (a, b) {
return a['width'] - b['width'];
});

$table
.unbind(trg.initialize)
//bind to FooTable initialize trigger
.bind(trg.initialize, function () {
//remove previous "state" (to "force" a resize)
$table.removeData('footable_info');
$table.data('breakpoint', '');

//trigger the FooTable resize
$table.trigger(trg.resize);

//remove the loading class
$table.removeClass(cls.loading);

//add the FooTable and loaded class
$table.addClass(cls.loaded).addClass(cls.main);

//raise the initialized event
ft.raise(evt.initialized);
})
.unbind(trg.redraw)
//bind to FooTable redraw trigger
.bind(trg.redraw, function () {
ft.redraw();
})
.unbind(trg.resize)
//bind to FooTable resize trigger
.bind(trg.resize, function () {
ft.resize();
})
.unbind(trg.expandFirstRow)
//bind to FooTable expandFirstRow trigger
.bind(trg.expandFirstRow, function () {
$table.find(opt.toggleSelector).first().not('.' + cls.detailShow).trigger(trg.toggleRow);
})
.unbind(trg.expandAll)
//bind to FooTable expandFirstRow trigger
.bind(trg.expandAll, function () {
$table.find(opt.toggleSelector).not('.' + cls.detailShow).trigger(trg.toggleRow);
})
.unbind(trg.collapseAll)
//bind to FooTable expandFirstRow trigger
.bind(trg.collapseAll, function () {
$table.find('.' + cls.detailShow).trigger(trg.toggleRow);
});

//trigger a FooTable initialize
$table.trigger(trg.initialize);

//bind to window resize
$window
.bind('resize.footable', function () {
ft.timers.resize.stop();
ft.timers.resize.start(function () {
ft.raise(trg.resize);
}, opt.delay);
});
};

ft.addRowToggle = function () {
if (!opt.addRowToggle) return;

var $table = $(ft.table),
hasToggleColumn = false;

//first remove all toggle spans
$table.find('span.' + cls.toggle).remove();

for (var c in ft.columns) {
var col = ft.columns[c];
if (col.toggle) {
hasToggleColumn = true;
var selector = '> tbody > tr:not(.' + cls.detail + ',.' + cls.disabled + ') > td:nth-child(' + (parseInt(col.index, 10) + 1) + '),' +
'> tbody > tr:not(.' + cls.detail + ',.' + cls.disabled + ') > th:nth-child(' + (parseInt(col.index, 10) + 1) + ')';
$table.find(selector).not('.' + cls.detailCell).prepend($(opt.toggleHTMLElement).addClass(cls.toggle));
return;
}
}
//check if we have an toggle column. If not then add it to the first column just to be safe
if (!hasToggleColumn) {
$table
.find('> tbody > tr:not(.' + cls.detail + ',.' + cls.disabled + ') > td:first-child')
.add('> tbody > tr:not(.' + cls.detail + ',.' + cls.disabled + ') > th:first-child')
.not('.' + cls.detailCell)
.prepend($(opt.toggleHTMLElement).addClass(cls.toggle));
}
};

ft.setColumnClasses = function () {
var $table = $(ft.table);
for (var c in ft.columns) {
var col = ft.columns[c];
if (col.className !== null) {
var selector = '', first = true;
$.each(col.matches, function (m, match) { //support for colspans
if (!first) selector += ', ';
selector += '> tbody > tr:not(.' + cls.detail + ') > td:nth-child(' + (parseInt(match, 10) + 1) + ')';
first = false;
});
//add the className to the cells specified by data-class="blah"
$table.find(selector).not('.' + cls.detailCell).addClass(col.className);
}
}
};

//moved this out into it's own function so that it can be called from other add-ons
ft.bindToggleSelectors = function () {
var $table = $(ft.table);

if (!ft.hasAnyBreakpointColumn()) return;

$table.find(opt.toggleSelector).unbind(trg.toggleRow).bind(trg.toggleRow, function (e) {
var $row = $(this).is('tr') ? $(this) : $(this).parents('tr:first');
ft.toggleDetail($row);
});

$table.find(opt.toggleSelector).unbind('click.footable').bind('click.footable', function (e) {
if ($table.is('.breakpoint') && $(e.target).is('td,th,.'+ cls.toggle)) {
$(this).trigger(trg.toggleRow);
}
});
};

ft.parse = function (cell, column) {
var parser = opt.parsers[column.type] || opt.parsers.alpha;
return parser(cell);
};

ft.getColumnData = function (th) {
var $th = $(th), hide = $th.data('hide'), index = $th.index();
hide = hide || '';
hide = jQuery.map(hide.split(','), function (a) {
return jQuery.trim(a);
});
var data = {
'index': index,
'hide': { },
'type': $th.data('type') || 'alpha',
'name': $th.data('name') || $.trim($th.text()),
'ignore': $th.data('ignore') || false,
'toggle': $th.data('toggle') || false,
'className': $th.data('class') || null,
'matches': [],
'names': { },
'group': $th.data('group') || null,
'groupName': null,
'isEditable': $th.data('editable')
};

if (data.group !== null) {
var $group = $(ft.table).find('> thead > tr.footable-group-row > th[data-group="' + data.group + '"], > thead > tr.footable-group-row > td[data-group="' + data.group + '"]').first();
data.groupName = ft.parse($group, { 'type': 'alpha' });
}

var pcolspan = parseInt($th.prev().attr('colspan') || 0, 10);
indexOffset += pcolspan > 1 ? pcolspan - 1 : 0;
var colspan = parseInt($th.attr('colspan') || 0, 10), curindex = data.index + indexOffset;
if (colspan > 1) {
var names = $th.data('names');
names = names || '';
names = names.split(',');
for (var i = 0; i < colspan; i++) {
data.matches.push(i + curindex);
if (i < names.length) data.names[i + curindex] = names[i];
}
} else {
data.matches.push(curindex);
}

data.hide['default'] = ($th.data('hide') === "all") || ($.inArray('default', hide) >= 0);

var hasBreakpoint = false;
for (var name in opt.breakpoints) {
data.hide[name] = ($th.data('hide') === "all") || ($.inArray(name, hide) >= 0);
hasBreakpoint = hasBreakpoint || data.hide[name];
}
data.hasBreakpoint = hasBreakpoint;
var e = ft.raise(evt.columnData, { 'column': { 'data': data, 'th': th } });
return e.column.data;
};

ft.getViewportWidth = function () {
return window.innerWidth || (document.body ? document.body.offsetWidth : 0);
};

ft.calculateWidth = function ($table, info) {
if (jQuery.isFunction(opt.calculateWidthOverride)) {
return opt.calculateWidthOverride($table, info);
}
if (info.viewportWidth < info.width) info.width = info.viewportWidth;
if (info.parentWidth < info.width) info.width = info.parentWidth;
return info;
};

ft.hasBreakpointColumn = function (breakpoint) {
for (var c in ft.columns) {
if (ft.columns[c].hide[breakpoint]) {
if (ft.columns[c].ignore) {
continue;
}
return true;
}
}
return false;
};

ft.hasAnyBreakpointColumn = function () {
for (var c in ft.columns) {
if (ft.columns[c].hasBreakpoint) {
return true;
}
}
return false;
};

ft.resize = function () {
var $table = $(ft.table);

if (!$table.is(':visible')) {
return;
} //we only care about FooTables that are visible

if (!ft.hasAnyBreakpointColumn()) {
$table.trigger(trg.redraw);
return;
} //we only care about FooTables that have breakpoints

var info = {
'width': $table.width(),                  //the table width
'viewportWidth': ft.getViewportWidth(),   //the width of the viewport
'parentWidth': $table.parent().width()    //the width of the parent
};

info = ft.calculateWidth($table, info);

var pinfo = $table.data('footable_info');
$table.data('footable_info', info);
ft.raise(evt.resizing, { 'old': pinfo, 'info': info });

// This (if) statement is here purely to make sure events aren't raised twice as mobile safari seems to do
if (!pinfo || (pinfo && pinfo.width && pinfo.width !== info.width)) {

var current = null, breakpoint;
for (var i = 0; i < ft.breakpoints.length; i++) {
breakpoint = ft.breakpoints[i];
if (breakpoint && breakpoint.width && info.width <= breakpoint.width) {
current = breakpoint;
break;
}
}

var breakpointName = (current === null ? 'default' : current['name']),
hasBreakpointFired = ft.hasBreakpointColumn(breakpointName),
previousBreakpoint = $table.data('breakpoint');

$table
.data('breakpoint', breakpointName)
.removeClass('default breakpoint').removeClass(ft.breakpointNames)
.addClass(breakpointName + (hasBreakpointFired ? ' breakpoint' : ''));

//only do something if the breakpoint has changed
if (breakpointName !== previousBreakpoint) {
//trigger a redraw
$table.trigger(trg.redraw);
//raise a breakpoint event
ft.raise(evt.breakpoint, { 'breakpoint': breakpointName, 'info': info });
}
}

ft.raise(evt.resized, { 'old': pinfo, 'info': info });
};

ft.redraw = function () {
//add the toggler to each row
ft.addRowToggle();

//bind the toggle selector click events
ft.bindToggleSelectors();

//set any cell classes defined for the columns
ft.setColumnClasses();

var $table = $(ft.table),
breakpointName = $table.data('breakpoint'),
hasBreakpointFired = ft.hasBreakpointColumn(breakpointName);

$table
.find('> tbody > tr:not(.' + cls.detail + ')').data('detail_created', false).end()
.find('> thead > tr:last-child > th')
.each(function () {
var data = ft.columns[$(this).index()], selector = '', first = true;
$.each(data.matches, function (m, match) {
if (!first) {
selector += ', ';
}
var count = match + 1;
selector += '> tbody > tr:not(.' + cls.detail + ') > td:nth-child(' + count + ')';
selector += ', > tfoot > tr:not(.' + cls.detail + ') > td:nth-child(' + count + ')';
selector += ', > colgroup > col:nth-child(' + count + ')';
first = false;
});

selector += ', > thead > tr[data-group-row="true"] > th[data-group="' + data.group + '"]';
var $column = $table.find(selector).add(this);
if (breakpointName !== '') {
if (data.hide[breakpointName] === false) $column.addClass('footable-visible').show();
else $column.removeClass('footable-visible').hide();
}

if ($table.find('> thead > tr.footable-group-row').length === 1) {
var $groupcols = $table.find('> thead > tr:last-child > th[data-group="' + data.group + '"]:visible, > thead > tr:last-child > th[data-group="' + data.group + '"]:visible'),
$group = $table.find('> thead > tr.footable-group-row > th[data-group="' + data.group + '"], > thead > tr.footable-group-row > td[data-group="' + data.group + '"]'),
groupspan = 0;

$.each($groupcols, function () {
groupspan += parseInt($(this).attr('colspan') || 1, 10);
});

if (groupspan > 0) $group.attr('colspan', groupspan).show();
else $group.hide();
}
})
.end()
.find('> tbody > tr.' + cls.detailShow).each(function () {
ft.createOrUpdateDetailRow(this);
});

$table.find("[data-bind-name]").each(function () {
ft.toggleInput(this);
});

$table.find('> tbody > tr.' + cls.detailShow + ':visible').each(function () {
var $next = $(this).next();
if ($next.hasClass(cls.detail)) {
if (!hasBreakpointFired) $next.hide();
else $next.show();
}
});

// adding .footable-first-column and .footable-last-column to the first and last th and td of each row in order to allow
// for styling if the first or last column is hidden (which won't work using :first-child or :last-child)
$table.find('> thead > tr > th.footable-last-column, > tbody > tr > td.footable-last-column').removeClass('footable-last-column');
$table.find('> thead > tr > th.footable-first-column, > tbody > tr > td.footable-first-column').removeClass('footable-first-column');
$table.find('> thead > tr, > tbody > tr')
.find('> th.footable-visible:last, > td.footable-visible:last')

.addClass('footable-last-column')
.end()
.find('> th.footable-visible:first, > td.footable-visible:first')
.addClass('footable-first-column');

ft.raise(evt.redrawn);
};

ft.toggleDetail = function (row) {
var $row = (row.jquery) ? row : $(row),
$next = $row.next();

//check if the row is already expanded
if ($row.hasClass(cls.detailShow)) {
$row.removeClass(cls.detailShow);

//only hide the next row if it's a detail row
if ($next.hasClass(cls.detail)) $next.hide();

ft.raise(evt.rowCollapsed, { 'row': $row[0] });

} else {
ft.createOrUpdateDetailRow($row[0]);
$row.addClass(cls.detailShow)
.next().show();

ft.raise(evt.rowExpanded, { 'row': $row[0] });
}
};

ft.removeRow = function (row) {
var $row = (row.jquery) ? row : $(row);
if ($row.hasClass(cls.detail)) {
$row = $row.prev();
}
var $next = $row.next();
if ($row.data('detail_created') === true) {
//remove the detail row
$next.remove();
}
$row.remove();

//raise event
ft.raise(evt.rowRemoved);
};

ft.appendRow = function (row) {
var $row = (row.jquery) ? row : $(row);
$(ft.table).find('tbody').append($row);

//redraw the table
ft.redraw();
};

ft.getColumnFromTdIndex = function (index) {
/// <summary>Returns the correct column data for the supplied index taking into account colspans.</summary>
/// <param name="index">The index to retrieve the column data for.</param>
/// <returns type="json">A JSON object containing the column data for the supplied index.</returns>
var result = null;
for (var column in ft.columns) {
if ($.inArray(index, ft.columns[column].matches) >= 0) {
result = ft.columns[column];
break;
}
}
return result;
};

ft.createOrUpdateDetailRow = function (actualRow) {
var $row = $(actualRow), $next = $row.next(), $detail, values = [];
if ($row.data('detail_created') === true) return true;

if ($row.is(':hidden')) return false; //if the row is hidden for some reason (perhaps filtered) then get out of here
ft.raise(evt.rowDetailUpdating, { 'row': $row, 'detail': $next });
$row.find('> td:hidden').each(function () {
var index = $(this).index(), column = ft.getColumnFromTdIndex(index), name = column.name;
if (column.ignore === true) return true;

if (index in column.names) name = column.names[index];

var bindName = $(this).attr("data-bind-name");
if (bindName != null && $(this).is(':empty')) {
var bindValue = $('.' + cls.detailInnerValue + '[' + 'data-bind-value="' + bindName + '"]');
$(this).html($(bindValue).contents().detach());
}
var display;
if (column.isEditable !== false && (column.isEditable || $(this).find(":input").length > 0)) {
if(bindName == null) {
bindName = "bind-" + $.now() + "-" + index;
$(this).attr("data-bind-name", bindName);
}
display = $(this).contents().detach();
}
if (!display) display = $(this).contents().clone(true, true);
values.push({ 'name': name, 'value': ft.parse(this, column), 'display': display, 'group': column.group, 'groupName': column.groupName, 'bindName': bindName });
return true;
});
if (values.length === 0) return false; //return if we don't have any data to show
var colspan = $row.find('> td:visible').length;
var exists = $next.hasClass(cls.detail);
if (!exists) { // Create
$next = $('<tr class="' + cls.detail + '"><td class="' + cls.detailCell + '"><div class="' + cls.detailInner + '"></div></td></tr>');
$row.after($next);
}
$next.find('> td:first').attr('colspan', colspan);
$detail = $next.find('.' + cls.detailInner).empty();
opt.createDetail($detail, values, opt.createGroupedDetail, opt.detailSeparator, cls);
$row.data('detail_created', true);
ft.raise(evt.rowDetailUpdated, { 'row': $row, 'detail': $next });
return !exists;
};

ft.raise = function (eventName, args) {

if (ft.options.debug === true && $.isFunction(ft.options.log)) ft.options.log(eventName, 'event');

args = args || { };
var def = { 'ft': ft };
$.extend(true, def, args);
var e = $.Event(eventName, def);
if (!e.ft) {
$.extend(true, e, def);
} //pre jQuery 1.6 which did not allow data to be passed to event object constructor
$(ft.table).trigger(e);
return e;
};

//reset the state of FooTable
ft.reset = function() {
var $table = $(ft.table);
$table.removeData('footable_info')
.data('breakpoint', '')
.removeClass(cls.loading)
.removeClass(cls.loaded);

$table.find(opt.toggleSelector).unbind(trg.toggleRow).unbind('click.footable');

$table.find('> tbody > tr').removeClass(cls.detailShow);

$table.find('> tbody > tr.' + cls.detail).remove();

ft.raise(evt.reset);
};

//Switch between row-detail and detail-show.
ft.toggleInput = function (column) {
var bindName = $(column).attr("data-bind-name");
if(bindName != null) {
var bindValue = $('.' + cls.detailInnerValue + '[' + 'data-bind-value="' + bindName + '"]');
if(bindValue != null) {
if($(column).is(":visible")) {
if(!$(bindValue).is(':empty')) $(column).html($(bindValue).contents().detach());
} else if(!$(column).is(':empty')) {
$(bindValue).html($(column).contents().detach());
}
}
}
};

ft.init();
return ft;
}
})(jQuery, window);








/************Script For Pagination***************/
(function ($, w, undefined) {
if (w.footable === undefined || w.footable === null)
throw new Error('Please check and make sure footable.js is included in the page and is loaded prior to this script.');

var defaults = {
paginate: true,
pageSize: 2,
pageNavigation: '.pagination',
firstText: '&laquo;',
previousText: '&lsaquo;',
nextText: '&rsaquo;',
lastText: '&raquo;',
limitNavigation: 0,
limitPreviousText: '...',
limitNextText: '...'
};

function pageInfo(ft) {
var $table = $(ft.table), data = $table.data();
this.pageNavigation = data.pageNavigation || ft.options.pageNavigation;
this.pageSize = data.pageSize || ft.options.pageSize;
this.firstText = data.firstText || ft.options.firstText;
this.previousText = data.previousText || ft.options.previousText;
this.nextText = data.nextText || ft.options.nextText;
this.lastText = data.lastText || ft.options.lastText;
this.limitNavigation = parseInt(data.limitNavigation || ft.options.limitNavigation || defaults.limitNavigation, 10);
this.limitPreviousText = data.limitPreviousText || ft.options.limitPreviousText;
this.limitNextText = data.limitNextText || ft.options.limitNextText;
this.limit = this.limitNavigation > 0;
this.currentPage = data.currentPage || 0;
this.pages = [];
this.control = false;
}

function Paginate() {
var p = this;
p.name = 'Footable Paginate';

p.init = function (ft) {
if (ft.options.paginate === true) {
if ($(ft.table).data('page') === false) return;
p.footable = ft;
$(ft.table)
.unbind('.paging')
.bind({
'footable_initialized.paging footable_row_removed.paging footable_redrawn.paging footable_sorted.paging footable_filtered.paging': function () {
p.setupPaging();
}
})
//save the filter object onto the table so we can access it later
.data('footable-paging', p);
}
};

p.setupPaging = function () {
var ft = p.footable,
$tbody = $(ft.table).find('> tbody');

ft.pageInfo = new pageInfo(ft);

p.createPages(ft, $tbody);
p.createNavigation(ft, $tbody);
p.fillPage(ft, $tbody, ft.pageInfo.currentPage);
};

p.createPages = function (ft, tbody) {
var pages = 1;
var info = ft.pageInfo;
var pageCount = pages * info.pageSize;
var page = [];
var lastPage = [];
info.pages = [];
var rows = tbody.find('> tr:not(.footable-filtered,.footable-row-detail)');
rows.each(function (i, row) {
page.push(row);
if (i === pageCount - 1) {
info.pages.push(page);
pages++;
pageCount = pages * info.pageSize;
page = [];
} else if (i >= rows.length - (rows.length % info.pageSize)) {
lastPage.push(row);
}
});
if (lastPage.length > 0) info.pages.push(lastPage);
if (info.currentPage >= info.pages.length) info.currentPage = info.pages.length - 1;
if (info.currentPage < 0) info.currentPage = 0;
if (info.pages.length === 1) {
//we only have a single page
$(ft.table).addClass('no-paging');
} else {
$(ft.table).removeClass('no-paging');
}
};

p.createNavigation = function (ft, tbody) {
var $nav = $(ft.table).find(ft.pageInfo.pageNavigation);
//if we cannot find the navigation control within the table, then try find it outside
if ($nav.length === 0) {
$nav = $(ft.pageInfo.pageNavigation);
//if the navigation control is inside another table, then get out
if ($nav.parents('table:first').length > 0 && $nav.parents('table:first') !== $(ft.table)) return;
//if we found more than one navigation control, write error to console
if ($nav.length > 1 && ft.options.debug === true) console.error('More than one pagination control was found!');
}
//if we still cannot find the control, then don't do anything
if ($nav.length === 0) return;
//if the nav is not a UL, then find or create a UL
if (!$nav.is('ul')) {
if ($nav.find('ul:first').length === 0) {
$nav.append('<ul />');
}
$nav = $nav.find('ul');
}
$nav.find('li').remove();
var info = ft.pageInfo;
info.control = $nav;
if (info.pages.length > 0) {
$nav.append('<li class="footable-page-arrow"><a data-page="first" href="#first">' + ft.pageInfo.firstText + '</a>');
$nav.append('<li class="footable-page-arrow"><a data-page="prev" href="#prev">' + ft.pageInfo.previousText + '</a></li>');
if (info.limit){
$nav.append('<li class="footable-page-arrow"><a data-page="limit-prev" href="#limit-prev">' + ft.pageInfo.limitPreviousText + '</a></li>');
}
if (!info.limit){
$.each(info.pages, function (i, page) {
if (page.length > 0) {
$nav.append('<li class="footable-page"><a data-page="' + i + '" href="#">' + (i + 1) + '</a></li>');
}
});
}
if (info.limit){
$nav.append('<li class="footable-page-arrow"><a data-page="limit-next" href="#limit-next">' + ft.pageInfo.limitNextText + '</a></li>');
p.createLimited($nav, info, 0);
}
$nav.append('<li class="footable-page-arrow"><a data-page="next" href="#next">' + ft.pageInfo.nextText + '</a></li>');
$nav.append('<li class="footable-page-arrow"><a data-page="last" href="#last">' + ft.pageInfo.lastText + '</a></li>');
}
$nav.off('click', 'a[data-page]').on('click', 'a[data-page]', function (e) {
e.preventDefault();
var page = $(this).data('page');
var newPage = info.currentPage;
if (page === 'first') {
newPage = 0;
} else if (page === 'prev') {
if (newPage > 0) newPage--;
} else if (page === 'next') {
if (newPage < info.pages.length - 1) newPage++;
} else if (page === 'last') {
newPage = info.pages.length - 1;
} else if (page === 'limit-prev') {
newPage = -1;
var first = $nav.find('.footable-page:first a').data('page');
p.createLimited($nav, info, first - info.limitNavigation);
p.setPagingClasses($nav, info.currentPage, info.pages.length);
} else if (page === 'limit-next') {
newPage = -1;
var last = $nav.find('.footable-page:last a').data('page');
p.createLimited($nav, info, last + 1);
p.setPagingClasses($nav, info.currentPage, info.pages.length);
} else {
newPage = page;
}
if (newPage >= 0){
if (info.limit && info.currentPage != newPage){
var start = newPage;
while (start % info.limitNavigation !== 0){ start -= 1; }
p.createLimited($nav, info, start);
}
p.paginate(ft, newPage);
}
});
p.setPagingClasses($nav, info.currentPage, info.pages.length);
};

p.createLimited = function(nav, info, start){
start = start || 0;
nav.find('li.footable-page').remove();
var i, page,
$prev = nav.find('li.footable-page-arrow > a[data-page="limit-prev"]').parent(),
$next = nav.find('li.footable-page-arrow > a[data-page="limit-next"]').parent();
for (i = info.pages.length - 1; i >=0 ; i--){
page = info.pages[i];
if (i >= start && i < start + info.limitNavigation && page.length > 0) {
$prev.after('<li class="footable-page"><a data-page="' + i + '" href="#">' + (i + 1) + '</a></li>');
}
}
if (start === 0){ $prev.hide(); }
else { $prev.show(); }
if (start + info.limitNavigation >= info.pages.length){ $next.hide(); }
else { $next.show(); }
};

p.paginate = function (ft, newPage) {
var info = ft.pageInfo;
if (info.currentPage !== newPage) {
var $tbody = $(ft.table).find('> tbody');

//raise a pre-pagin event so that we can cancel the paging if needed
var event = ft.raise('footable_paging', { page: newPage, size: info.pageSize });
if (event && event.result === false) return;

p.fillPage(ft, $tbody, newPage);
info.control.find('li').removeClass('active disabled');
p.setPagingClasses(info.control, info.currentPage, info.pages.length);
}
};

p.setPagingClasses = function (nav, currentPage, pageCount) {
nav.find('li.footable-page > a[data-page=' + currentPage + ']').parent().addClass('active');
if (currentPage >= pageCount - 1) {
nav.find('li.footable-page-arrow > a[data-page="next"]').parent().addClass('disabled');
nav.find('li.footable-page-arrow > a[data-page="last"]').parent().addClass('disabled');
}
if (currentPage < 1) {
nav.find('li.footable-page-arrow > a[data-page="first"]').parent().addClass('disabled');
nav.find('li.footable-page-arrow > a[data-page="prev"]').parent().addClass('disabled');
}
};

p.fillPage = function (ft, tbody, pageNumber) {
ft.pageInfo.currentPage = pageNumber;
$(ft.table).data('currentPage', pageNumber);
tbody.find('> tr').hide();
$(ft.pageInfo.pages[pageNumber]).each(function () {
p.showRow(this, ft);
});
ft.raise('footable_page_filled');
};

p.showRow = function (row, ft) {
var $row = $(row), $next = $row.next(), $table = $(ft.table);
if ($table.hasClass('breakpoint') && $row.hasClass('footable-detail-show') && $next.hasClass('footable-row-detail')) {
$row.add($next).show();
ft.createOrUpdateDetailRow(row);
}
else $row.show();
};
}

w.footable.plugins.register(Paginate, defaults);

})(jQuery, window);









/************Core Script For table***************/
$(function () {
$('table').footable({ bookmarkable: { enabled: true }}).bind({
'footable_filtering': function (e) {
var selected = $('.filter-status').find(':selected').text();
if (selected && selected.length > 0) {
e.filter += (e.filter && e.filter.length > 0) ? ' ' + selected : selected;
e.clear = !e.filter;
}
},
'footable_filtered': function() {
var count = $('table.demo tbody tr:not(.footable-filtered)').length;
$('.row-count').html(count + ' rows found');
}
});

$('.clear-filter').click(function (e) {
e.preventDefault();
$('.filter-status').val('');
$('table.demo').trigger('footable_clear_filter');
$('.row-count').html('');
});

$('.filter-status').change(function (e) {
e.preventDefault();
$('table.demo').data('footable-filter').filter( $('#filter').val() );
});
});
///////////////////////////////////////////////Footable Responsive Table JS Plugin End////////////////////////////////////////////////////////
/*!
 * Author: Abdullah A Almsaeed
 * Date: 4 Jan 2014
 * Description:
 *      This file should be included in all pages
 !**/

/*
 * Global variables. If you change any of these vars, don't forget 
 * to change the values in the less files!
 */
var left_side_width = 220; //Sidebar width in pixels

$(function() {
    "use strict";

    //Enable sidebar toggle
    $("[data-toggle='offcanvas']").click(function(e) {
        e.preventDefault();

        //If window is small enough, enable sidebar push menu
        if ($(window).width() <= 992) {
            $('.row-offcanvas').toggleClass('active');
            $('.left-side').removeClass("collapse-left");
            $(".right-side").removeClass("strech");
            $('.row-offcanvas').toggleClass("relative");
        } else {
            //Else, enable content streching
            $('.left-side').toggleClass("collapse-left");
            $(".right-side").toggleClass("strech");
        }
    });

    //Add hover support for touch devices
    $('.btn').bind('touchstart', function() {
        $(this).addClass('hover');
    }).bind('touchend', function() {
        $(this).removeClass('hover');
    });

    //Activate tooltips
    $("[data-toggle='tooltip']").tooltip();

    /*     
     * Add collapse and remove events to boxes
     */
    $("[data-widget='collapse']").click(function() {
        //Find the box parent        
        var box = $(this).parents(".box").first();
        //Find the body and the footer
        var bf = box.find(".box-body, .box-footer");
        if (!box.hasClass("collapsed-box")) {
            box.addClass("collapsed-box");
            bf.slideUp();
        } else {
            box.removeClass("collapsed-box");
            bf.slideDown();
        }
    });

    /*
     * ADD SLIMSCROLL TO THE TOP NAV DROPDOWNS
     * ---------------------------------------
     */
    $(".navbar .menu").slimscroll({
        height: "200px",
        alwaysVisible: false,
        size: "3px"
    }).css("width", "100%");

    /*
     * INITIALIZE BUTTON TOGGLE
     * ------------------------
     */
    $('.btn-group[data-toggle="btn-toggle"]').each(function() {
        var group = $(this);
        $(this).find(".btn").click(function(e) {
            group.find(".btn.active").removeClass("active");
            $(this).addClass("active");
            e.preventDefault();
        });

    });

    $("[data-widget='remove']").click(function() {
        //Find the box parent        
        var box = $(this).parents(".box").first();
        box.slideUp();
    });

    /* Sidebar tree view */
    $(".sidebar .treeview").tree();

    /* 
     * Make sure that the sidebar is streched full height
     * ---------------------------------------------
     * We are gonna assign a min-height value every time the
     * wrapper gets resized and upon page load. We will use
     * Ben Alman's method for detecting the resize event.
     * 
     **/
    function _fix() {
        //Get window height and the wrapper height
        var height = $(window).height() - $("body > .header").height();
       // $(".wrapper").css("min-height", height + "px");
        var content = $(".wrapper").height();
        //If the wrapper height is greater than the window
        if (content > height)
            //then set sidebar height to the wrapper
            $(".left-side").css("min-height", content + "px");
        else {
            //Otherwise, set the sidebar to the height of the window
            $(".left-side").css("min-height", height + "px");
        }
    }
    
    
   /* function _fix() {
        //Get window height and the wrapper height
        var height = $(window).height() - $("body > .header").height();
        $(".wrapper").css("min-height", height + "px");
        var content = $(".wrapper").height();
        //If the wrapper height is greater than the window
        if (content > height)
            //then set sidebar height to the wrapper
            $(".left-side, html, body").css("min-height", content + "px");
        else {
            //Otherwise, set the sidebar to the height of the window
            $(".left-side, html, body").css("min-height", height + "px");
        }
    }*/
    //Fire upon load
    _fix();
    //Fire when wrapper is resized
    $(".wrapper").resize(function() {
        _fix();
        fix_sidebar();
    });

    //Fix the fixed layout sidebar scroll bug
    fix_sidebar();

    /*
     * We are gonna initialize all checkbox and radio inputs to 
     * iCheck plugin in.
     * You can find the documentation at http://fronteed.com/iCheck/
     */
    $("input[type='checkbox'], input[type='radio']").iCheck({
        checkboxClass: 'icheckbox_minimal',
        radioClass: 'iradio_minimal'
    });

    /* For demo purposes */
//    var demo = $("<div />").css({
//        position: "fixed",
//        top: "150px",
//        right: "0",
//        background: "rgba(0, 0, 0, 0.7)",
//        "border-radius": "5px 0px 0px 5px",
//        padding: "10px 15px",
//        "font-size": "16px",
//        "z-index": "999999",
//        cursor: "pointer",
//        color: "#ddd"
//    }).html("<i class='fa fa-gear'></i>").addClass("no-print");
//
//    var demo_settings = $("<div />").css({
//        "padding": "10px",
//        position: "fixed",
//        top: "130px",
//        right: "-200px",
//        background: "#fff",
//        border: "3px solid rgba(0, 0, 0, 0.7)",
//        "width": "200px",
//        "z-index": "999999"
//    }).addClass("no-print");
//    demo_settings.append(
//            "<h4 style='margin: 0 0 5px 0; border-bottom: 1px dashed #ddd; padding-bottom: 3px;'>Layout Options</h4>"
//            + "<div class='form-group no-margin'>"
//            + "<div class='.checkbox'>"
//            + "<label>"
//            + "<input type='checkbox' onchange='change_layout();'/> "
//            + "Fixed layout"
//            + "</label>"
//            + "</div>"
//            + "</div>"
//            );
//    demo_settings.append(
//            "<h4 style='margin: 0 0 5px 0; border-bottom: 1px dashed #ddd; padding-bottom: 3px;'>Skins</h4>"
//            + "<div class='form-group no-margin'>"
//            + "<div class='.radio'>"
//            + "<label>"
//            + "<input name='skins' type='radio' onchange='change_skin(\"skin-black\");' /> "
//            + "Black"
//            + "</label>"
//            + "</div>"
//            + "</div>"
//
//            + "<div class='form-group no-margin'>"
//            + "<div class='.radio'>"
//            + "<label>"
//            + "<input name='skins' type='radio' onchange='change_skin(\"skin-blue\");' checked='checked'/> "
//            + "Blue"
//            + "</label>"
//            + "</div>"
//            + "</div>"
//            );
//
//    demo.click(function() {
//        if (!$(this).hasClass("open")) {
//            $(this).css("right", "200px");
//            demo_settings.css("right", "0");
//            $(this).addClass("open");
//        } else {
//            $(this).css("right", "0");
//            demo_settings.css("right", "-200px");
//            $(this).removeClass("open")
//        }
//    });
//
//    $("body").append(demo);
//    $("body").append(demo_settings);
});
function fix_sidebar() {
    //Make sure the body tag has the .fixed class
    if (!$("body").hasClass("fixed")) {
        return;
    }

    //Add slimscroll
    $(".sidebar").slimscroll({
        height: ($(window).height() - $(".header").height()) + "px",
        color: "rgba(0,0,0,0.2)"
    });
}
function change_layout() {
    $("body").toggleClass("fixed");
    fix_sidebar();
}
function change_skin(cls) {
    $("body").removeClass("skin-blue skin-black");
    $("body").addClass(cls);
}
/*END DEMO*/
$(window).load(function() {
    /*! pace 0.4.17 */
    (function() {
        var a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, q, r, s, t, u, v, w, x, y, z, A, B, C, D, E, F, G, H, I, J, K, L, M, N, O, P, Q, R, S, T, U, V = [].slice, W = {}.hasOwnProperty, X = function(a, b) {
            function c() {
                this.constructor = a
            }
            for (var d in b)
                W.call(b, d) && (a[d] = b[d]);
            return c.prototype = b.prototype, a.prototype = new c, a.__super__ = b.prototype, a
        }, Y = [].indexOf || function(a) {
            for (var b = 0, c = this.length; c > b; b++)
                if (b in this && this[b] === a)
                    return b;
            return-1
        };
        for (t = {catchupTime:500, initialRate:.03, minTime:500, ghostTime:500, maxProgressPerFrame:10, easeFactor:1.25, startOnPageLoad:!0, restartOnPushState:!0, restartOnRequestAfter:500, target:"body", elements:{checkInterval:100, selectors:["body"]}, eventLag:{minSamples:10, sampleCount:3, lagThreshold:3}, ajax:{trackMethods:["GET"], trackWebSockets:!1}}, B = function() {
            var a;
            return null != (a = "undefined" != typeof performance && null !== performance ? "function" == typeof performance.now ? performance.now() : void 0 : void 0) ? a : +new Date
        }, D = window.requestAnimationFrame || window.mozRequestAnimationFrame || window.webkitRequestAnimationFrame || window.msRequestAnimationFrame, s = window.cancelAnimationFrame || window.mozCancelAnimationFrame, null == D && (D = function(a) {
            return setTimeout(a, 50)
        }, s = function(a) {
            return clearTimeout(a)
        }), F = function(a) {
            var b, c;
            return b = B(), (c = function() {
                var d;
                return d = B() - b, d >= 33 ? (b = B(), a(d, function() {
                    return D(c)
                })) : setTimeout(c, 33 - d)
            })()
        }, E = function() {
            var a, b, c;
            return c = arguments[0], b = arguments[1], a = 3 <= arguments.length ? V.call(arguments, 2) : [], "function" == typeof c[b] ? c[b].apply(c, a) : c[b]
        }, u = function() {
            var a, b, c, d, e, f, g;
            for (b = arguments[0], d = 2 <= arguments.length?V.call(arguments, 1):[], f = 0, g = d.length; g > f; f++)
                if (c = d[f])
                    for (a in c)
                        W.call(c, a) && (e = c[a], null != b[a] && "object" == typeof b[a] && null != e && "object" == typeof e ? u(b[a], e) : b[a] = e);
            return b
        }, p = function(a) {
            var b, c, d, e, f;
            for (c = b = 0, e = 0, f = a.length; f > e; e++)
                d = a[e], c += Math.abs(d), b++;
            return c / b
        }, w = function(a, b) {
            var c, d, e;
            if (null == a && (a = "options"), null == b && (b = !0), e = document.querySelector("[data-pace-" + a + "]")) {
                if (c = e.getAttribute("data-pace-" + a), !b)
                    return c;
                try {
                    return JSON.parse(c)
                } catch (f) {
                    return d = f, "undefined" != typeof console && null !== console ? console.error("Error parsing inline pace options", d) : void 0
                }
            }
        }, g = function() {
            function a() {
            }
            return a.prototype.on = function(a, b, c, d) {
                var e;
                return null == d && (d = !1), null == this.bindings && (this.bindings = {}), null == (e = this.bindings)[a] && (e[a] = []), this.bindings[a].push({handler: b, ctx: c, once: d})
            }, a.prototype.once = function(a, b, c) {
                return this.on(a, b, c, !0)
            }, a.prototype.off = function(a, b) {
                var c, d, e;
                if (null != (null != (d = this.bindings) ? d[a] : void 0)) {
                    if (null == b)
                        return delete this.bindings[a];
                    for (c = 0, e = []; c < this.bindings[a].length; )
                        this.bindings[a][c].handler === b ? e.push(this.bindings[a].splice(c, 1)) : e.push(c++);
                    return e
                }
            }, a.prototype.trigger = function() {
                var a, b, c, d, e, f, g, h, i;
                if (c = arguments[0], a = 2 <= arguments.length ? V.call(arguments, 1) : [], null != (g = this.bindings) ? g[c] : void 0) {
                    for (e = 0, i = []; e < this.bindings[c].length; )
                        h = this.bindings[c][e], d = h.handler, b = h.ctx, f = h.once, d.apply(null != b ? b : this, a), f ? i.push(this.bindings[c].splice(e, 1)) : i.push(e++);
                    return i
                }
            }, a
        }(), null == window.Pace && (window.Pace = {}), u(Pace, g.prototype), C = Pace.options = u({}, t, window.paceOptions, w()), S = ["ajax", "document", "eventLag", "elements"], O = 0, Q = S.length; Q > O; O++)
            I = S[O], C[I] === !0 && (C[I] = t[I]);
        i = function(a) {
            function b() {
                return T = b.__super__.constructor.apply(this, arguments)
            }
            return X(b, a), b
        }(Error), b = function() {
            function a() {
                this.progress = 0
            }
            return a.prototype.getElement = function() {
                var a;
                if (null == this.el) {
                    if (a = document.querySelector(C.target), !a)
                        throw new i;
                    this.el = document.createElement("div"), this.el.className = "pace pace-active", document.body.className = document.body.className.replace("pace-done", ""), document.body.className += " pace-running", this.el.innerHTML = '<div class="pace-progress">\n  <div class="pace-progress-inner"></div>\n</div>\n<div class="pace-activity"></div>', null != a.firstChild ? a.insertBefore(this.el, a.firstChild) : a.appendChild(this.el)
                }
                return this.el
            }, a.prototype.finish = function() {
                var a;
                return a = this.getElement(), a.className = a.className.replace("pace-active", ""), a.className += " pace-inactive", document.body.className = document.body.className.replace("pace-running", ""), document.body.className += " pace-done"
            }, a.prototype.update = function(a) {
                return this.progress = a, this.render()
            }, a.prototype.destroy = function() {
                try {
                    this.getElement().parentNode.removeChild(this.getElement())
                } catch (a) {
                    i = a
                }
                return this.el = void 0
            }, a.prototype.render = function() {
                var a, b;
                return null == document.querySelector(C.target) ? !1 : (a = this.getElement(), a.children[0].style.width = "" + this.progress + "%", (!this.lastRenderedProgress || this.lastRenderedProgress | 0 !== this.progress | 0) && (a.children[0].setAttribute("data-progress-text", "" + (0 | this.progress) + "%"), this.progress >= 100 ? b = "99" : (b = this.progress < 10 ? "0" : "", b += 0 | this.progress), a.children[0].setAttribute("data-progress", "" + b)), this.lastRenderedProgress = this.progress)
            }, a.prototype.done = function() {
                return this.progress >= 100
            }, a
        }(), h = function() {
            function a() {
                this.bindings = {}
            }
            return a.prototype.trigger = function(a, b) {
                var c, d, e, f, g;
                if (null != this.bindings[a]) {
                    for (f = this.bindings[a], g = [], d = 0, e = f.length; e > d; d++)
                        c = f[d], g.push(c.call(this, b));
                    return g
                }
            }, a.prototype.on = function(a, b) {
                var c;
                return null == (c = this.bindings)[a] && (c[a] = []), this.bindings[a].push(b)
            }, a
        }(), N = window.XMLHttpRequest, M = window.XDomainRequest, L = window.WebSocket, v = function(a, b) {
            var c, d, e, f;
            f = [];
            for (d in b.prototype)
                try {
                    e = b.prototype[d], null == a[d] && "function" != typeof e ? f.push(a[d] = e) : f.push(void 0)
                } catch (g) {
                    c = g
                }
            return f
        }, z = [], Pace.ignore = function() {
            var a, b, c;
            return b = arguments[0], a = 2 <= arguments.length ? V.call(arguments, 1) : [], z.unshift("ignore"), c = b.apply(null, a), z.shift(), c
        }, Pace.track = function() {
            var a, b, c;
            return b = arguments[0], a = 2 <= arguments.length ? V.call(arguments, 1) : [], z.unshift("track"), c = b.apply(null, a), z.shift(), c
        }, H = function(a) {
            var b;
            if (null == a && (a = "GET"), "track" === z[0])
                return"force";
            if (!z.length && C.ajax) {
                if ("socket" === a && C.ajax.trackWebSockets)
                    return!0;
                if (b = a.toUpperCase(), Y.call(C.ajax.trackMethods, b) >= 0)
                    return!0
            }
            return!1
        }, j = function(a) {
            function b() {
                var a, c = this;
                b.__super__.constructor.apply(this, arguments), a = function(a) {
                    var b;
                    return b = a.open, a.open = function(d, e) {
                        return H(d) && c.trigger("request", {type: d, url: e, request: a}), b.apply(a, arguments)
                    }
                }, window.XMLHttpRequest = function(b) {
                    var c;
                    return c = new N(b), a(c), c
                }, v(window.XMLHttpRequest, N), null != M && (window.XDomainRequest = function() {
                    var b;
                    return b = new M, a(b), b
                }, v(window.XDomainRequest, M)), null != L && C.ajax.trackWebSockets && (window.WebSocket = function(a, b) {
                    var d;
                    return d = new L(a, b), H("socket") && c.trigger("request", {type: "socket", url: a, protocols: b, request: d}), d
                }, v(window.WebSocket, L))
            }
            return X(b, a), b
        }(h), P = null, x = function() {
            return null == P && (P = new j), P
        }, x().on("request", function(b) {
            var c, d, e, f;
            return f = b.type, e = b.request, Pace.running || C.restartOnRequestAfter === !1 && "force" !== H(f) ? void 0 : (d = arguments, c = C.restartOnRequestAfter || 0, "boolean" == typeof c && (c = 0), setTimeout(function() {
                var b, c, g, h, i, j;
                if (b = "socket" === f ? e.readyState < 2 : 0 < (h = e.readyState) && 4 > h) {
                    for (Pace.restart(), i = Pace.sources, j = [], c = 0, g = i.length; g > c; c++) {
                        if (I = i[c], I instanceof a) {
                            I.watch.apply(I, d);
                            break
                        }
                        j.push(void 0)
                    }
                    return j
                }
            }, c))
        }), a = function() {
            function a() {
                var a = this;
                this.elements = [], x().on("request", function() {
                    return a.watch.apply(a, arguments)
                })
            }
            return a.prototype.watch = function(a) {
                var b, c, d;
                return d = a.type, b = a.request, c = "socket" === d ? new m(b) : new n(b), this.elements.push(c)
            }, a
        }(), n = function() {
            function a(a) {
                var b, c, d, e, f, g, h = this;
                if (this.progress = 0, null != window.ProgressEvent)
                    for (c = null, a.addEventListener("progress", function(a) {
                        return h.progress = a.lengthComputable ? 100 * a.loaded / a.total : h.progress + (100 - h.progress) / 2
                    }), g = ["load", "abort", "timeout", "error"], d = 0, e = g.length; e > d; d++)
                        b = g[d], a.addEventListener(b, function() {
                            return h.progress = 100
                        });
                else
                    f = a.onreadystatechange, a.onreadystatechange = function() {
                        var b;
                        return 0 === (b = a.readyState) || 4 === b ? h.progress = 100 : 3 === a.readyState && (h.progress = 50), "function" == typeof f ? f.apply(null, arguments) : void 0
                    }
            }
            return a
        }(), m = function() {
            function a(a) {
                var b, c, d, e, f = this;
                for (this.progress = 0, e = ["error", "open"], c = 0, d = e.length; d > c; c++)
                    b = e[c], a.addEventListener(b, function() {
                        return f.progress = 100
                    })
            }
            return a
        }(), d = function() {
            function a(a) {
                var b, c, d, f;
                for (null == a && (a = {}), this.elements = [], null == a.selectors && (a.selectors = []), f = a.selectors, c = 0, d = f.length; d > c; c++)
                    b = f[c], this.elements.push(new e(b))
            }
            return a
        }(), e = function() {
            function a(a) {
                this.selector = a, this.progress = 0, this.check()
            }
            return a.prototype.check = function() {
                var a = this;
                return document.querySelector(this.selector) ? this.done() : setTimeout(function() {
                    return a.check()
                }, C.elements.checkInterval)
            }, a.prototype.done = function() {
                return this.progress = 100
            }, a
        }(), c = function() {
            function a() {
                var a, b, c = this;
                this.progress = null != (b = this.states[document.readyState]) ? b : 100, a = document.onreadystatechange, document.onreadystatechange = function() {
                    return null != c.states[document.readyState] && (c.progress = c.states[document.readyState]), "function" == typeof a ? a.apply(null, arguments) : void 0
                }
            }
            return a.prototype.states = {loading: 0, interactive: 50, complete: 100}, a
        }(), f = function() {
            function a() {
                var a, b, c, d, e, f = this;
                this.progress = 0, a = 0, e = [], d = 0, c = B(), b = setInterval(function() {
                    var g;
                    return g = B() - c - 50, c = B(), e.push(g), e.length > C.eventLag.sampleCount && e.shift(), a = p(e), ++d >= C.eventLag.minSamples && a < C.eventLag.lagThreshold ? (f.progress = 100, clearInterval(b)) : f.progress = 100 * (3 / (a + 3))
                }, 50)
            }
            return a
        }(), l = function() {
            function a(a) {
                this.source = a, this.last = this.sinceLastUpdate = 0, this.rate = C.initialRate, this.catchup = 0, this.progress = this.lastProgress = 0, null != this.source && (this.progress = E(this.source, "progress"))
            }
            return a.prototype.tick = function(a, b) {
                var c;
                return null == b && (b = E(this.source, "progress")), b >= 100 && (this.done = !0), b === this.last ? this.sinceLastUpdate += a : (this.sinceLastUpdate && (this.rate = (b - this.last) / this.sinceLastUpdate), this.catchup = (b - this.progress) / C.catchupTime, this.sinceLastUpdate = 0, this.last = b), b > this.progress && (this.progress += this.catchup * a), c = 1 - Math.pow(this.progress / 100, C.easeFactor), this.progress += c * this.rate * a, this.progress = Math.min(this.lastProgress + C.maxProgressPerFrame, this.progress), this.progress = Math.max(0, this.progress), this.progress = Math.min(100, this.progress), this.lastProgress = this.progress, this.progress
            }, a
        }(), J = null, G = null, q = null, K = null, o = null, r = null, Pace.running = !1, y = function() {
            return C.restartOnPushState ? Pace.restart() : void 0
        }, null != window.history.pushState && (R = window.history.pushState, window.history.pushState = function() {
            return y(), R.apply(window.history, arguments)
        }), null != window.history.replaceState && (U = window.history.replaceState, window.history.replaceState = function() {
            return y(), U.apply(window.history, arguments)
        }), k = {ajax: a, elements: d, document: c, eventLag: f}, (A = function() {
            var a, c, d, e, f, g, h, i;
            for (Pace.sources = J = [], g = ["ajax", "elements", "document", "eventLag"], c = 0, e = g.length; e > c; c++)
                a = g[c], C[a] !== !1 && J.push(new k[a](C[a]));
            for (i = null != (h = C.extraSources)?h:[], d = 0, f = i.length; f > d; d++)
                I = i[d], J.push(new I(C));
            return Pace.bar = q = new b, G = [], K = new l
        })(), Pace.stop = function() {
            return Pace.trigger("stop"), Pace.running = !1, q.destroy(), r = !0, null != o && ("function" == typeof s && s(o), o = null), A()
        }, Pace.restart = function() {
            return Pace.trigger("restart"), Pace.stop(), Pace.start()
        }, Pace.go = function() {
            return Pace.running = !0, q.render(), r = !1, o = F(function(a, b) {
                var c, d, e, f, g, h, i, j, k, m, n, o, p, s, t, u, v;
                for (j = 100 - q.progress, d = o = 0, e = !0, h = p = 0, t = J.length; t > p; h = ++p)
                    for (I = J[h], m = null != G[h]?G[h]:G[h] = [], g = null != (v = I.elements)?v:[I], i = s = 0, u = g.length; u > s; i = ++s)
                        f = g[i], k = null != m[i] ? m[i] : m[i] = new l(f), e &= k.done, k.done || (d++, o += k.tick(a));
                return c = o / d, q.update(K.tick(a, c)), n = B(), q.done() || e || r ? (q.update(100), Pace.trigger("done"), setTimeout(function() {
                    return q.finish(), Pace.running = !1, Pace.trigger("hide")
                }, Math.max(C.ghostTime, Math.min(C.minTime, B() - n)))) : b()
            })
        }, Pace.start = function(a) {
            u(C, a), Pace.running = !0;
            try {
                q.render()
            } catch (b) {
                i = b
            }
            return document.querySelector(".pace") ? (Pace.trigger("start"), Pace.go()) : setTimeout(Pace.start, 50)
        }, "function" == typeof define && define.amd ? define('theme-app', [], function() {
            return Pace
        }) : "object" == typeof exports ? module.exports = Pace : C.startOnPageLoad && Pace.start()
    }).call(this);
});

/* 
 * BOX REFRESH BUTTON 
 * ------------------
 * This is a custom plugin to use with the compenet BOX. It allows you to add
 * a refresh button to the box. It converts the box's state to a loading state.
 * 
 * USAGE:
 *  $("#box-widget").boxRefresh( options );
 * */
(function($) {
    "use strict";

    $.fn.boxRefresh = function(options) {

        // Render options
        var settings = $.extend({
            //Refressh button selector
            trigger: ".refresh-btn",
            //File source to be loaded (e.g: ajax/src.php)
            source: "",
            //Callbacks
            onLoadStart: function(box) {
            }, //Right after the button has been clicked
            onLoadDone: function(box) {
            } //When the source has been loaded

        }, options);

        //The overlay
        var overlay = $('<div class="overlay"></div><div class="loading-img"></div>');

        return this.each(function() {
            //if a source is specified
            if (settings.source === "") {
                if (console) {
                    console.log("Please specify a source first - boxRefresh()");
                }
                return;
            }
            //the box
            var box = $(this);
            //the button
            var rBtn = box.find(settings.trigger).first();

            //On trigger click
            rBtn.click(function(e) {
                e.preventDefault();
                //Add loading overlay
                start(box);

                //Perform ajax call
                box.find(".box-body").load(settings.source, function() {
                    done(box);
                });


            });

        });

        function start(box) {
            //Add overlay and loading img
            box.append(overlay);

            settings.onLoadStart.call(box);
        }

        function done(box) {
            //Remove overlay and loading img
            box.find(overlay).remove();

            settings.onLoadDone.call(box);
        }

    };

})(jQuery);

/*
 * SIDEBAR MENU
 * ------------
 * This is a custom plugin for the sidebar menu. It provides a tree view.
 * 
 * Usage:
 * $(".sidebar).tree();
 * 
 * Note: This plugin does not accept any options. Instead, it only requires a class
 *       added to the element that contains a sub-menu.
 *       
 * When used with the sidebar, for example, it would look something like this:
 * <ul class='sidebar-menu'>
 *      <li class="treeview active">
 *          <a href="#>Menu</a>
 *          <ul class='treeview-menu'>
 *              <li class='active'><a href=#>Level 1</a></li>
 *          </ul>
 *      </li>
 * </ul>
 * 
 * Add .active class to <li> elements if you want the menu to be open automatically
 * on page load. See above for an example.
 */
(function($) {
    "use strict";

    $.fn.tree = function() {

        return this.each(function() {
            var btn = $(this).children("a").first();
            var menu = $(this).children(".treeview-menu").first();
            var isActive = $(this).hasClass('active');

            //initialize already active menus
            if (isActive) {
                menu.show();
                btn.children(".fa-angle-left").first().removeClass("fa-angle-left").addClass("fa-angle-down");
            }
            //Slide open or close the menu on link click
            btn.click(function(e) {
                e.preventDefault();
                if (isActive) {
                    //Slide up to close menu
                    menu.slideUp();
                    isActive = false;
                    btn.children(".fa-angle-down").first().removeClass("fa-angle-down").addClass("fa-angle-left");
                    btn.parent("li").removeClass("active");
                } else {
                    //Slide down to open menu
                    menu.slideDown();
                    isActive = true;
                    btn.children(".fa-angle-left").first().removeClass("fa-angle-left").addClass("fa-angle-down");
                    btn.parent("li").addClass("active");
                }
            });

            /* Add margins to submenu elements to give it a tree look */
            menu.find("li > a").each(function() {
                var pad = parseInt($(this).css("margin-left")) + 10;

                $(this).css({"margin-left": pad + "px"});
            });

        });

    };


}(jQuery));

/*
 * TODO LIST CUSTOM PLUGIN
 * -----------------------
 * This plugin depends on iCheck plugin for checkbox and radio inputs
 */
(function($) {
    "use strict";

    $.fn.todolist = function(options) {
        // Render options
        var settings = $.extend({
            //When the user checks the input
            onCheck: function(ele) {
            },
            //When the user unchecks the input
            onUncheck: function(ele) {
            }
        }, options);

        return this.each(function() {
            $('input', this).on('ifChecked', function(event) {
                var ele = $(this).parents("li").first();
                ele.toggleClass("done");
                settings.onCheck.call(ele);
            });

            $('input', this).on('ifUnchecked', function(event) {
                var ele = $(this).parents("li").first();
                ele.toggleClass("done");
                settings.onUncheck.call(ele);
            });
        });
    };

}(jQuery));

/* CENTER ELEMENTS */
(function($) {
    "use strict";
    jQuery.fn.center = function(parent) {
        if (parent) {
            parent = this.parent();
        } else {
            parent = window;
        }
        this.css({
            "position": "absolute",
            "top": ((($(parent).height() - this.outerHeight()) / 2) + $(parent).scrollTop() + "px"),
            "left": ((($(parent).width() - this.outerWidth()) / 2) + $(parent).scrollLeft() + "px")
        });
        return this;
    }
}(jQuery));

/*
 * jQuery resize event - v1.1 - 3/14/2010
 * http://benalman.com/projects/jquery-resize-plugin/
 * 
 * Copyright (c) 2010 "Cowboy" Ben Alman
 * Dual licensed under the MIT and GPL licenses.
 * http://benalman.com/about/license/
 */
(function($, h, c) {
    var a = $([]), e = $.resize = $.extend($.resize, {}), i, k = "setTimeout", j = "resize", d = j + "-special-event", b = "delay", f = "throttleWindow";
    e[b] = 250;
    e[f] = true;
    $.event.special[j] = {setup: function() {
            if (!e[f] && this[k]) {
                return false;
            }
            var l = $(this);
            a = a.add(l);
            $.data(this, d, {w: l.width(), h: l.height()});
            if (a.length === 1) {
                g();
            }
        }, teardown: function() {
            if (!e[f] && this[k]) {
                return false
            }
            var l = $(this);
            a = a.not(l);
            l.removeData(d);
            if (!a.length) {
                clearTimeout(i);
            }
        }, add: function(l) {
            if (!e[f] && this[k]) {
                return false
            }
            var n;
            function m(s, o, p) {
                var q = $(this), r = $.data(this, d);
                r.w = o !== c ? o : q.width();
                r.h = p !== c ? p : q.height();
                n.apply(this, arguments)
            }
            if ($.isFunction(l)) {
                n = l;
                return m
            } else {
                n = l.handler;
                l.handler = m
            }
        }};
    function g() {
        i = h[k](function() {
            a.each(function() {
                var n = $(this), m = n.width(), l = n.height(), o = $.data(this, d);
                if (m !== o.w || l !== o.h) {
                    n.trigger(j, [o.w = m, o.h = l])
                }
            });
            g()
        }, e[b])
    }}
)(jQuery, this);

/*!
 * SlimScroll https://github.com/rochal/jQuery-slimScroll
 * =======================================================
 * 
 * Copyright (c) 2011 Piotr Rochala (http://rocha.la) Dual licensed under the MIT 
 */
(function(f) {
    jQuery.fn.extend({slimScroll: function(h) {
            var a = f.extend({width: "auto", height: "250px", size: "7px", color: "#000", position: "right", distance: "1px", start: "top", opacity: 0.4, alwaysVisible: !1, disableFadeOut: !1, railVisible: !1, railColor: "#333", railOpacity: 0.2, railDraggable: !0, railClass: "slimScrollRail", barClass: "slimScrollBar", wrapperClass: "slimScrollDiv", allowPageScroll: !1, wheelStep: 20, touchScrollStep: 200, borderRadius: "0px", railBorderRadius: "0px"}, h);
            this.each(function() {
                function r(d) {
                    if (s) {
                        d = d ||
                                window.event;
                        var c = 0;
                        d.wheelDelta && (c = -d.wheelDelta / 120);
                        d.detail && (c = d.detail / 3);
                        f(d.target || d.srcTarget || d.srcElement).closest("." + a.wrapperClass).is(b.parent()) && m(c, !0);
                        d.preventDefault && !k && d.preventDefault();
                        k || (d.returnValue = !1)
                    }
                }
                function m(d, f, h) {
                    k = !1;
                    var e = d, g = b.outerHeight() - c.outerHeight();
                    f && (e = parseInt(c.css("top")) + d * parseInt(a.wheelStep) / 100 * c.outerHeight(), e = Math.min(Math.max(e, 0), g), e = 0 < d ? Math.ceil(e) : Math.floor(e), c.css({top: e + "px"}));
                    l = parseInt(c.css("top")) / (b.outerHeight() - c.outerHeight());
                    e = l * (b[0].scrollHeight - b.outerHeight());
                    h && (e = d, d = e / b[0].scrollHeight * b.outerHeight(), d = Math.min(Math.max(d, 0), g), c.css({top: d + "px"}));
                    b.scrollTop(e);
                    b.trigger("slimscrolling", ~~e);
                    v();
                    p()
                }
                function C() {
                    window.addEventListener ? (this.addEventListener("DOMMouseScroll", r, !1), this.addEventListener("mousewheel", r, !1), this.addEventListener("MozMousePixelScroll", r, !1)) : document.attachEvent("onmousewheel", r)
                }
                function w() {
                    u = Math.max(b.outerHeight() / b[0].scrollHeight * b.outerHeight(), D);
                    c.css({height: u + "px"});
                    var a = u == b.outerHeight() ? "none" : "block";
                    c.css({display: a})
                }
                function v() {
                    w();
                    clearTimeout(A);
                    l == ~~l ? (k = a.allowPageScroll, B != l && b.trigger("slimscroll", 0 == ~~l ? "top" : "bottom")) : k = !1;
                    B = l;
                    u >= b.outerHeight() ? k = !0 : (c.stop(!0, !0).fadeIn("fast"), a.railVisible && g.stop(!0, !0).fadeIn("fast"))
                }
                function p() {
                    a.alwaysVisible || (A = setTimeout(function() {
                        a.disableFadeOut && s || (x || y) || (c.fadeOut("slow"), g.fadeOut("slow"))
                    }, 1E3))
                }
                var s, x, y, A, z, u, l, B, D = 30, k = !1, b = f(this);
                if (b.parent().hasClass(a.wrapperClass)) {
                    var n = b.scrollTop(),
                            c = b.parent().find("." + a.barClass), g = b.parent().find("." + a.railClass);
                    w();
                    if (f.isPlainObject(h)) {
                        if ("height"in h && "auto" == h.height) {
                            b.parent().css("height", "auto");
                            b.css("height", "auto");
                            var q = b.parent().parent().height();
                            b.parent().css("height", q);
                            b.css("height", q)
                        }
                        if ("scrollTo"in h)
                            n = parseInt(a.scrollTo);
                        else if ("scrollBy"in h)
                            n += parseInt(a.scrollBy);
                        else if ("destroy"in h) {
                            c.remove();
                            g.remove();
                            b.unwrap();
                            return
                        }
                        m(n, !1, !0)
                    }
                } else {
                    a.height = "auto" == a.height ? b.parent().height() : a.height;
                    n = f("<div></div>").addClass(a.wrapperClass).css({position: "relative",
                        overflow: "hidden", width: a.width, height: a.height});
                    b.css({overflow: "hidden", width: a.width, height: a.height});
                    var g = f("<div></div>").addClass(a.railClass).css({width: a.size, height: "100%", position: "absolute", top: 0, display: a.alwaysVisible && a.railVisible ? "block" : "none", "border-radius": a.railBorderRadius, background: a.railColor, opacity: a.railOpacity, zIndex: 90}), c = f("<div></div>").addClass(a.barClass).css({background: a.color, width: a.size, position: "absolute", top: 0, opacity: a.opacity, display: a.alwaysVisible ?
                                "block" : "none", "border-radius": a.borderRadius, BorderRadius: a.borderRadius, MozBorderRadius: a.borderRadius, WebkitBorderRadius: a.borderRadius, zIndex: 99}), q = "right" == a.position ? {right: a.distance} : {left: a.distance};
                    g.css(q);
                    c.css(q);
                    b.wrap(n);
                    b.parent().append(c);
                    b.parent().append(g);
                    a.railDraggable && c.bind("mousedown", function(a) {
                        var b = f(document);
                        y = !0;
                        t = parseFloat(c.css("top"));
                        pageY = a.pageY;
                        b.bind("mousemove.slimscroll", function(a) {
                            currTop = t + a.pageY - pageY;
                            c.css("top", currTop);
                            m(0, c.position().top, !1)
                        });
                        b.bind("mouseup.slimscroll", function(a) {
                            y = !1;
                            p();
                            b.unbind(".slimscroll")
                        });
                        return!1
                    }).bind("selectstart.slimscroll", function(a) {
                        a.stopPropagation();
                        a.preventDefault();
                        return!1
                    });
                    g.hover(function() {
                        v()
                    }, function() {
                        p()
                    });
                    c.hover(function() {
                        x = !0
                    }, function() {
                        x = !1
                    });
                    b.hover(function() {
                        s = !0;
                        v();
                        p()
                    }, function() {
                        s = !1;
                        p()
                    });
                    b.bind("touchstart", function(a, b) {
                        a.originalEvent.touches.length && (z = a.originalEvent.touches[0].pageY)
                    });
                    b.bind("touchmove", function(b) {
                        k || b.originalEvent.preventDefault();
                        b.originalEvent.touches.length &&
                                (m((z - b.originalEvent.touches[0].pageY) / a.touchScrollStep, !0), z = b.originalEvent.touches[0].pageY)
                    });
                    w();
                    "bottom" === a.start ? (c.css({top: b.outerHeight() - c.outerHeight()}), m(0, !0)) : "top" !== a.start && (m(f(a.start).position().top, null, !0), a.alwaysVisible || c.hide());
                    C()
                }
            });
            return this
        }});
    jQuery.fn.extend({slimscroll: jQuery.fn.slimScroll})
})(jQuery);

/*! iCheck v1.0.1 by Damir Sultanov, http://git.io/arlzeA, MIT Licensed */
(function(h) {
    function F(a, b, d) {
        var c = a[0], e = /er/.test(d) ? m : /bl/.test(d) ? s : l, f = d == H ? {checked: c[l], disabled: c[s], indeterminate: "true" == a.attr(m) || "false" == a.attr(w)} : c[e];
        if (/^(ch|di|in)/.test(d) && !f)
            D(a, e);
        else if (/^(un|en|de)/.test(d) && f)
            t(a, e);
        else if (d == H)
            for (e in f)
                f[e] ? D(a, e, !0) : t(a, e, !0);
        else if (!b || "toggle" == d) {
            if (!b)
                a[p]("ifClicked");
            f ? c[n] !== u && t(a, e) : D(a, e)
        }
    }
    function D(a, b, d) {
        var c = a[0], e = a.parent(), f = b == l, A = b == m, B = b == s, K = A ? w : f ? E : "enabled", p = k(a, K + x(c[n])), N = k(a, b + x(c[n]));
        if (!0 !== c[b]) {
            if (!d &&
                    b == l && c[n] == u && c.name) {
                var C = a.closest("form"), r = 'input[name="' + c.name + '"]', r = C.length ? C.find(r) : h(r);
                r.each(function() {
                    this !== c && h(this).data(q) && t(h(this), b)
                })
            }
            A ? (c[b] = !0, c[l] && t(a, l, "force")) : (d || (c[b] = !0), f && c[m] && t(a, m, !1));
            L(a, f, b, d)
        }
        c[s] && k(a, y, !0) && e.find("." + I).css(y, "default");
        e[v](N || k(a, b) || "");
        B ? e.attr("aria-disabled", "true") : e.attr("aria-checked", A ? "mixed" : "true");
        e[z](p || k(a, K) || "")
    }
    function t(a, b, d) {
        var c = a[0], e = a.parent(), f = b == l, h = b == m, q = b == s, p = h ? w : f ? E : "enabled", t = k(a, p + x(c[n])),
                u = k(a, b + x(c[n]));
        if (!1 !== c[b]) {
            if (h || !d || "force" == d)
                c[b] = !1;
            L(a, f, p, d)
        }
        !c[s] && k(a, y, !0) && e.find("." + I).css(y, "pointer");
        e[z](u || k(a, b) || "");
        q ? e.attr("aria-disabled", "false") : e.attr("aria-checked", "false");
        e[v](t || k(a, p) || "")
    }
    function M(a, b) {
        if (a.data(q)) {
            a.parent().html(a.attr("style", a.data(q).s || ""));
            if (b)
                a[p](b);
            a.off(".i").unwrap();
            h(G + '[for="' + a[0].id + '"]').add(a.closest(G)).off(".i")
        }
    }
    function k(a, b, d) {
        if (a.data(q))
            return a.data(q).o[b + (d ? "" : "Class")]
    }
    function x(a) {
        return a.charAt(0).toUpperCase() +
                a.slice(1)
    }
    function L(a, b, d, c) {
        if (!c) {
            if (b)
                a[p]("ifToggled");
            a[p]("ifChanged")[p]("if" + x(d))
        }
    }
    var q = "iCheck", I = q + "-helper", u = "radio", l = "checked", E = "un" + l, s = "disabled", w = "determinate", m = "in" + w, H = "update", n = "type", v = "addClass", z = "removeClass", p = "trigger", G = "label", y = "cursor", J = /ipad|iphone|ipod|android|blackberry|windows phone|opera mini|silk/i.test(navigator.userAgent);
    h.fn[q] = function(a, b) {
        var d = 'input[type="checkbox"], input[type="' + u + '"]', c = h(), e = function(a) {
            a.each(function() {
                var a = h(this);
                c = a.is(d) ?
                        c.add(a) : c.add(a.find(d))
            })
        };
        if (/^(check|uncheck|toggle|indeterminate|determinate|disable|enable|update|destroy)$/i.test(a))
            return a = a.toLowerCase(), e(this), c.each(function() {
                var c = h(this);
                "destroy" == a ? M(c, "ifDestroyed") : F(c, !0, a);
                h.isFunction(b) && b()
            });
        if ("object" != typeof a && a)
            return this;
        var f = h.extend({checkedClass: l, disabledClass: s, indeterminateClass: m, labelHover: !0, aria: !1}, a), k = f.handle, B = f.hoverClass || "hover", x = f.focusClass || "focus", w = f.activeClass || "active", y = !!f.labelHover, C = f.labelHoverClass ||
                "hover", r = ("" + f.increaseArea).replace("%", "") | 0;
        if ("checkbox" == k || k == u)
            d = 'input[type="' + k + '"]';
        -50 > r && (r = -50);
        e(this);
        return c.each(function() {
            var a = h(this);
            M(a);
            var c = this, b = c.id, e = -r + "%", d = 100 + 2 * r + "%", d = {position: "absolute", top: e, left: e, display: "block", width: d, height: d, margin: 0, padding: 0, background: "#fff", border: 0, opacity: 0}, e = J ? {position: "absolute", visibility: "hidden"} : r ? d : {position: "absolute", opacity: 0}, k = "checkbox" == c[n] ? f.checkboxClass || "icheckbox" : f.radioClass || "i" + u, m = h(G + '[for="' + b + '"]').add(a.closest(G)),
                    A = !!f.aria, E = q + "-" + Math.random().toString(36).replace("0.", ""), g = '<div class="' + k + '" ' + (A ? 'role="' + c[n] + '" ' : "");
            m.length && A && m.each(function() {
                g += 'aria-labelledby="';
                this.id ? g += this.id : (this.id = E, g += E);
                g += '"'
            });
            g = a.wrap(g + "/>")[p]("ifCreated").parent().append(f.insert);
            d = h('<ins class="' + I + '"/>').css(d).appendTo(g);
            a.data(q, {o: f, s: a.attr("style")}).css(e);
            f.inheritClass && g[v](c.className || "");
            f.inheritID && b && g.attr("id", q + "-" + b);
            "static" == g.css("position") && g.css("position", "relative");
            F(a, !0, H);
            if (m.length)
                m.on("click.i mouseover.i mouseout.i touchbegin.i touchend.i", function(b) {
                    var d = b[n], e = h(this);
                    if (!c[s]) {
                        if ("click" == d) {
                            if (h(b.target).is("a"))
                                return;
                            F(a, !1, !0)
                        } else
                            y && (/ut|nd/.test(d) ? (g[z](B), e[z](C)) : (g[v](B), e[v](C)));
                        if (J)
                            b.stopPropagation();
                        else
                            return!1
                    }
                });
            a.on("click.i focus.i blur.i keyup.i keydown.i keypress.i", function(b) {
                var d = b[n];
                b = b.keyCode;
                if ("click" == d)
                    return!1;
                if ("keydown" == d && 32 == b)
                    return c[n] == u && c[l] || (c[l] ? t(a, l) : D(a, l)), !1;
                if ("keyup" == d && c[n] == u)
                    !c[l] && D(a, l);
                else if (/us|ur/.test(d))
                    g["blur" ==
                            d ? z : v](x)
            });
            d.on("click mousedown mouseup mouseover mouseout touchbegin.i touchend.i", function(b) {
                var d = b[n], e = /wn|up/.test(d) ? w : B;
                if (!c[s]) {
                    if ("click" == d)
                        F(a, !1, !0);
                    else {
                        if (/wn|er|in/.test(d))
                            g[v](e);
                        else
                            g[z](e + " " + w);
                        if (m.length && y && e == B)
                            m[/ut|nd/.test(d) ? z : v](C)
                    }
                    if (J)
                        b.stopPropagation();
                    else
                        return!1
                }
            })
        })
    }
})(window.jQuery || window.Zepto);


