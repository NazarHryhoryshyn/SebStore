function showUserOrders(login){
	$.ajax({
	    type : "GET",
	    url : "/webstore/admin/user/orders",
	    data : {
		    "login" : login
	    },
	    success: function(data){
	    	$("#modal-user-orders-title" ).text(login + " orders");
	    	$("#modal-user-orders-orders" ).text("");
	    	var countPrice = 0;
	    	for (var i=0; i < data.length; i++) {
	    		var prods = "";
	    		for (var j=0; j < data[i].products.length; j++) {
	    			prods += "<li>"+data[i].products[j].name+" "+data[i].products[j].price+"&#8372;</li>";
	    			countPrice += data[i].products[j].price;
	    		}
	    		 $("#modal-user-orders-orders" ).append(
		    				"<div class='u-order'>"+
							"	<p>№ "+data[i].id+"</p>"+
							"	<ul>"+ 
							prods +
							"	</ul>"+
							"	Count price: "+countPrice+"&#8372;</div>");
	    		}
	    }
	});
}

function changeAdmiStatus(cb, login) {
	  $.ajax({
		     method: "POST",
		     url: "/webstore/admin/changeIsAdmin",
		     data: { login: login, status : cb.checked}
		});
	}

function changeBlockedStatus(cb, login) {
	  $.ajax({
		     method: "POST",
		     url: "/webstore/admin/changeIsBlocked",
		     data: { login: login, status : cb.checked}
		});
	}