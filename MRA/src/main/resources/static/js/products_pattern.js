
function clearPattern()
{
$('#patid').val('');
$('#pattern_name').val('');
}

function loadPattern()
{
	$('#dataTables-example3').DataTable().clear();
	var editButtonHtml = "<a class=\"btn btn-primary btn-sm\" id=\"btnEditPat\" data-toggle=\"modal\" data-target=\"#myModal3\"  ><i class=\"fa fa-edit \"></i> Edit</a>";
	var deleteButtonHtml = "<a class=\"btn btn-danger btn-sm\" id=\"btnDeletePat\" ><i class=\"fa fa-trash-o \"></i> Delete</a>";
	$.ajax({
		type: "GET",
		url: baseUrl + getAllPatterns,
		data: { data: "" },
		cache: false,
		success: function (result) {
			data = JSON.parse(JSON.stringify(result.data));
			console.log(result);
			if (result.status == 'OK') {
				$.each(result.data, function (index, data) {
					var rowIndex = $('#dataTables-example3').dataTable().fnAddData([
						data.pattern_name,
						"<img class='zoom' src='"+data.pattern_file+"'/>",
						editButtonHtml + "  " + deleteButtonHtml]
					);
					var row = $('#dataTables-example3').dataTable().fnGetNodes(rowIndex);
					$(row).attr('id', data.id);
				});
			
				
				
			}
			else {
				$.toast({
				    text: "Unable to Load Collection Table",
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

$(document).on("click", "#btnAddPat", function (event) {
	event.preventDefault();
	if (($('#pattern_name').val() != '')){
		if($('#patid').val !='')
		{
			var pattern = {
					"id":$('#patid').val(),
					"pattern_name": $('#pattern_name').val()};
			savePattern(pattern);
			
		}
			
	}
	else {
		if ($('#pattern_name').val() == '') {
			$('#pattern_name').addClass("field-red");
			$('#pattern_name').removeClass("field-ok");
		}
		else {
			$('#pattern_name').addClass("field-ok");
			$('#pattern_name').removeClass("field-red");
		}
		if ($('#pattern_file').val() == '') {
			$('#pattern_file').addClass("field-red");
			$('#pattern_file').removeClass("field-ok");
		}
		else {
			$('#pattern_file').addClass("field-ok");
			$('#pattern_file').removeClass("field-red");
		}
		
	}


});



function savePattern(pattern) {
	
	 var file = $('#patternUpload')[0];
	 console.log(file);
	 var form = new FormData(file);
	 form.append("name",pattern.pattern_name);
	 form.append("id",pattern.id);
	 console.log(form);
	$.ajax({
		type: "POST",
		url: baseUrl + addPattern,
		enctype : 'multipart/form-data',
		data:form,
		processData: false,
        contentType: false,
		async: true,
		cache: false,
		timeout: 60000,
		success: function (data) {

			console.log("SUCCESS : ", data);
			if (data.status == 'OK') {
				$.toast({
				    text: "Pattern Operation Success",
				    hideAfter: 2000,
				    icon: 'success',
				    loader: false,
				    showHideTransition: 'fade',
				    stack: false,
				    position: 'top-left'
  	
				});
				$('.btn-default').click();
				$('.close').click();
				loadPattern();
				
				
			}
			else {
				$.toast({
				    text: "Unable to do Pattern Operation",
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

$(document).on("click", "#btnEditPat", function () {
	var pid = $(this).closest('tr').attr('id');
	var row = $(this).closest('tr');
	$('#patid').val(pid);
	$('#pattern_name').val(row.find("td:eq(0)").text());
});


$(document).on("click", "#btnDeletePat", function () {
	var pid = $(this).closest('tr').attr('id');
	deletePatternbyId(pid);
});

function deletePatternbyId(pid) {
	$.ajax({
		type: "DELETE",
		headers: {
			'content-type': 'application/json'
		},
		url: baseUrl + deletePattern+"/"+pid,
		crossDomain: true,
		async: true,
		timeout: 60000,
		success: function (data) {
			console.log("SUCCESS : ", data);
			if (data.status == 'OK') {
				$.toast({
				    text: "Pattern Deleted Successfully",
				    hideAfter: 2000,
				    icon: 'success',
				    loader: false,
				    showHideTransition: 'fade',
				    stack: false,
				    position: 'top-left'
  	
				});
				loadPattern();
				
			}
			else {
				$.toast({
				    text: "Unable to Delete Collection",
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



