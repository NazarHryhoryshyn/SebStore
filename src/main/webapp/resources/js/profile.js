function changeFirstName(){
    	$.ajax({
    	     method: "POST",
    	     url: "/webstore/profile/changefname",
    	     data: { firstname: $( "#newFirstName" ).val()}
    	});
    }

function changeLastName(){
	$.ajax({
	     method: "POST",
	     url: "/webstore/profile/changelname",
	     data: { lastname: $( "#newLastName" ).val()}
	});
}

function changeEmail(){
	$.ajax({
	     method: "POST",
	     url: "/webstore/profile/changeEmail",
	     data: { email: $( "#newEmail" ).val()}
	});
}

function changeTelephone(){
	$.ajax({
	     method: "POST",
	     url: "/webstore/profile/changeTelephone",
	     data: { telephone: $( "#newTelephone" ).val()}
	});
}

function changeSex(){
	$.ajax({
	     method: "POST",
	     url: "/webstore/profile/changeSex",
	     data: { sex: $( "#newSex" ).val()}
	});
}

function changePassword(){
	if($( "#newPass" ).val() != $( "#newPass2" ).val()){
		alert("new passwords are not same");
		return;
	}
	$.ajax({
	     method: "POST",
	     url: "/webstore/profile/changePassword",
	     data: { oldPassword: $( "#oldPass" ).val(), newPassword : $("#newPass").val()}
	});
}