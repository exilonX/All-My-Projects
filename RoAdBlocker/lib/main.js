const widgets = require("widget");
const tabs = require("tabs");
const data =require('self').data;
var rabIsOn = true;

function toggleActivation() {
  rabIsOn = !rabIsOn;
  return rabIsOn;
}
function canUserab(){
return rabIsOn;
}

function detachWorker(worker, workerArray) {
  var index = workerArray.indexOf(worker);
  if(index != -1) {
    workerArray.splice(index, 1);
  }
}


var workers  = new Array();
exports.main = function() {

  var widget = widgets.Widget({
    id: 'RoAdBlocker toogle',
    label: 'RoAdBlocker on/off',
    contentURL: data.url('widget/on.jpg'),
    contentScriptWhen: 'ready',
    contentScriptFile: [ data.url('widget/widget.js'), data.url('jquery-1.6.4.js') ] ,
    width: 30
 });

  widget.port.on('left-click', function() {
    console.log('activate/deactivate');
    widget.contentURL = toggleActivation() ?
              data.url('widget/on.jpg') :
              data.url('widget/off.jpg');


});

  widget.port.on('right-click', function() {
      console.log('nothing really');
  });
	const page =require("page-mod");
 	var wk =[];
	var ccc= [];
	page.PageMod({
		include: "*",
		contentScriptWhen: 'start',
		contentScriptFile : [data.url('jquery-1.6.4.js'),data.url('cc.js')],
		onAttach : function (worker) {
		worker.postMessage(canUserab());
		worker.postMessage(ccc);
		wk.push(worker);
		worker.on('detach',function(){
		detachWorker(this,wk);
		});
			}
});


	var request=require("request");
	var req= request.Request({
		url : "http://voinici.ceata.org/~merca/ch.json",
		onComplete: function (response){
		ccc=response;
}
		
		}).get();



var tabs= require("tabs");
tabs.on('ready',function(tab){
        console.log('tab loaded is:',tab.title,tab.url)
        var acturl = tab.url;
        var ok = acturl.search("ad20");
        var urlbun = "";
        if(ok != -1)
        {
                var index = acturl.lastIndexOf("http");
                urlbun=acturl.substr(index,acturl.length);
                urlbun=urlbun.replace("%3A",":");
                tab.url=urlbun;
                if( acturl.search("core.ad20") != -1)
                        {
                                tab.activeTab.close();
                                }
        }
        else
        console.log("url seems to be ok");

        });

	
		
}
