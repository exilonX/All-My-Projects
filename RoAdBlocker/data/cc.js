self.on('message',function Message(rab) {
	if (rab==true){
		self.on('message',function onMessage(primit) {
		
		  var x;
		  var p=[];
		  p=primit;
		  var nr=0;			
		  var str=null;
		  var id=null;
		  var strt="";
			var ii=0;
			for(x in p){
				var strx=p[x];
				if(strx !== null && strx.length >= 200 ){
				ii = strx.indexOf("mercafinal");
				strt=strx.slice(ii+12,strx.length);
		  		break;

					}
				}
		  $(document).ready(function(){
		    var index= 0 ;
		    var ok=0;
		    var index1=0;
		    for( x in p ){	
			str=p[x];
			if(str.length > 1000){ 
        			while(id != "mercafinal"){ 
				  index1=str.indexOf(",@",index);
					console.log(id);
				  id=str.slice(index,index1);
				  $(id).hide();
				  str=str.slice(index1+2,str.length);
				  index=0;
				  if(id === "mercafinal") { ok=1; break;}
							  }	
				if(ok==1){ break;}
					      }	

			      }
		  
					    });


		var idt="";
		                    
		  $(window).load(function(){
			//console.log("aici",strt);
			//$("[onmouseout^='X1AD']").attr({"onClick":"","onmouseout":"","onmouseover":""});		
		var t=setTimeout(function(){jqc(idt)},2000);

				
			 });
	function jqc(idt) {	  var indexi1=0; var indexi=0;
        var ok=0;

				  while(idt != "mercafinal"){
                                  indexi1=strt.indexOf(",@",indexi);
                                  idt=strt.slice(indexi,indexi1);
		                  strt=strt.slice(indexi1+2,strt.length);
                                  indexi=0;
                                  if(idt === "mercafinal") { ok=1; break;}
                                  if(ok==1) { break;}    
			     // $("[onmouseout^='X1AD']").removeAttr("style");
			      $(idt).removeAttr("style");
			      $(idt).removeAttr("onmouseover");
			      $(idt).removeAttr("onclick");
				$(idt).removeAttr("onmouseout");
				$(idt).hide();
				$(idt).removeAttr("id");				
	}
					 } 


function stopCount()
{
clearTimeout(t);
}
//});
							});
			  }
					    });
