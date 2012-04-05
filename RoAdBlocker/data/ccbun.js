self.on('message',function onMessage(rab) {
	if (rab==true){
		self.on('message',function onMessage(primit) {

		  var x;
		  var p=[];
		  p=primit;
		  var nr=0;			
		  var str=null;
		  var id=null;
		  var strt="";
		  $(document).ready(function(){
		    var index= 0 ;
		    var ok=0;
		    var index1=0;
		    for( x in p ){	
			str=p[x];
			if(str.length > 1000){ 
        			while(id != "mercafinal"){ 
				  index1=str.indexOf(",@",index);
				  id=str.slice(index,index1);
				  $(id).hide();
				  str=str.slice(index1+2,str.length);
				  index=0;
				  if(id === "mercafinal") { ok=1; strt=str; break;}
							  }	
				if(ok==1){ break;}
					      }	

			      }
		  
					    });

		  $(window).load(function(){
			//console.log("aici",strt);
			//$("[onmouseout^='X1AD']").attr({"onClick":"","onmouseout":"","onmouseover":""});
			window.setTimeout(function(){
			      $("[onmouseout^='X1AD']").removeAttr("style");
			      $("[onmouseout^='X1AD']").removeAttr("onmouseover");
			      $("[onmouseout^='X1AD']").removeAttr("onclick");
						    },1000);
				
				  });




								});
			  }
					    });
