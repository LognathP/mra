


function clearCollection()
{
$('#clid').val('');
$('#color_name').val('');
$('#color_code').val('');
}

function loadColor()
{
	$('#dataTables-example4').DataTable().clear();
	var editButtonHtml = "<a class=\"btn btn-primary btn-sm\" id=\"btnEditCol\" data-toggle=\"modal\" data-target=\"#myModal4\"  ><i class=\"fa fa-edit \"></i> Edit</a>";
	var deleteButtonHtml = "<a class=\"btn btn-danger btn-sm\" id=\"btnDeleteCol\" ><i class=\"fa fa-trash-o \"></i> Delete</a>";
	$.ajax({
		type: "GET",
		url: baseUrl + getAllColors,
		data: { data: "" },
		cache: false,
		success: function (result) {
			data = JSON.parse(JSON.stringify(result.data));
			console.log(result);
			if (result.status == 'OK') {
				$.each(result.data, function (index, data) {
					var rowIndex = $('#dataTables-example4').dataTable().fnAddData([
						data.color_name,
						data.color_code,
						"<div class='zoom' style='margin: 0 auto;height:30px;width:30px;background-color:"+data.color_code+"'><div>",
						editButtonHtml + "  " + deleteButtonHtml]
					);
					var row = $('#dataTables-example4').dataTable().fnGetNodes(rowIndex);
					$(row).attr('id', data.id);
				});
			
				
				
			}
			else {
				$.toast({
				    text: "Unable to Load Color Table",
				    hideAfter: 2000,
				    icon: 'error',
				    loader: false,
				    showHideTransition: 'fade',
				    stack: false,
				    position: 'top-left'
  	
				});
	
			}

		},
		error: function (e) {

			console.log("ERROR : ", e);
			$.toast({
			    text: 'Error in Server Connection',
			    hideAfter: 2000,
			    icon: 'error',
			    loader: false,
			    showHideTransition: 'fade',
			    stack: false,
			    position: 'top-left'
	
			});
	
		}

	});

}

function isHexColor (hex) {
	var pattern = new RegExp("^#([a-fA-F0-9]){3}$|[a-fA-F0-9]{6}$");
	  if(pattern.test(hex)) {
		  return true;
		  } else {
			  return false;
		  }
	}
$(document).on("click", "#btnAddCol", function (event) {
	event.preventDefault();
	if (($('#color_name').val() != '') && ($('#color_code').val() != '')){
		
		if(!isHexColor($('#color_code').val()))
		{
			$.toast({
			    text: "Invalid Color Code",
			    hideAfter: 2000,
			    icon: 'error',
			    loader: false,
			    showHideTransition: 'fade',
			    stack: false,
			    position: 'top-left'
	
			});
		}
		else
		{
			if($('#clid').val !='')
			{
				var color = {
						"id":$('#clid').val(),
						"color_name": $('#color_name').val(),
						"color_code": $('#color_code').val()
						};
				saveColor(color);
				
			}
		}
		
			
	}
	else {
		if ($('#color_name').val() == '') {
			$('#color_name').addClass("field-red");
			$('#color_name').removeClass("field-ok");
		}
		else {
			$('#color_name').addClass("field-ok");
			$('#color_name').removeClass("field-red");
		}
		if ($('#color_code').val() == '') {
			$('#color_code').addClass("field-red");
			$('#color_code').removeClass("field-ok");
		}
		else {
			$('#color_code').addClass("field-ok");
			$('#color_code').removeClass("field-red");
		}
	}

});



function saveColor(color) {
	$.ajax({
		type: "POST",
		headers: {
			'content-type': 'application/json'
		},
		url: baseUrl + addColor,
		dataType: "json",
		data:  JSON.stringify(color),
		processData: false,
		contentType: false,
		crossDomain: true,
		async: true,
		cache: false,
		timeout: 60000,
		success: function (data) {

			console.log("SUCCESS : ", data);
			if (data.status == 'OK') {
				$.toast({
				    text: "Color Operation Success",
				    hideAfter: 2000,
				    icon: 'success',
				    loader: false,
				    showHideTransition: 'fade',
				    stack: false,
				    position: 'top-left'
  	
				});
				$('.btn-default').click();
				$('.close').click();
				loadColor();
				
				
			}
			else {
				$.toast({
				    text: "Unable to do Color Operation",
				    hideAfter: 2000,
				    icon: 'error',
				    loader: false,
				    showHideTransition: 'fade',
				    stack: false,
				    position: 'top-left'
  	
				});
				$('.btn-default').click();
				$('.close').click();

			}

		},
		error: function (e) {
			console.log("ERROR : ", e);
			$.toast({
			    text: 'Error in Server Connection',
			    hideAfter: 2000,
			    icon: 'error',
			    loader: false,
			    showHideTransition: 'fade',
			    stack: false,
			    position: 'top-left'
	
			});
			$('.btn-default').click();
			$('.close').click();
		}
	});

}

$(document).on("click", "#btnEditCol", function () {
	var pid = $(this).closest('tr').attr('id');
	var row = $(this).closest('tr');
	$('#clid').val(pid);
	$('#color_name').val(row.find("td:eq(0)").text());
	$('#color_code').val(row.find("td:eq(1)").text());
});


$(document).on("click", "#btnDeleteCol", function () {
	var pid = $(this).closest('tr').attr('id');
	deleteColorbyId(pid);
});

function deleteColorbyId(pid) {
	$.ajax({
		type: "DELETE",
		headers: {
			'content-type': 'application/json'
		},
		url: baseUrl + deleteColor+"/"+pid,
		crossDomain: true,
		async: true,
		timeout: 60000,
		success: function (data) {
			console.log("SUCCESS : ", data);
			if (data.status == 'OK') {
				$.toast({
				    text: "Color Deleted Successfully",
				    hideAfter: 2000,
				    icon: 'success',
				    loader: false,
				    showHideTransition: 'fade',
				    stack: false,
				    position: 'top-left'
  	
				});
				loadColor();
				
			}
			else {
				$.toast({
				    text: "Unable to Delete Color",
				    hideAfter: 2000,
				    icon: 'error',
				    loader: false,
				    showHideTransition: 'fade',
				    stack: false,
				    position: 'top-left'
  	
				});
	
			}

		},
		error: function (e) {
			console.log("ERROR : ", e);
			$.toast({
			    text: 'Error in Server Connection',
			    hideAfter: 2000,
			    icon: 'error',
			    loader: false,
			    showHideTransition: 'fade',
			    stack: false,
			    position: 'top-left'
	
			});
	
		}
	});
}



