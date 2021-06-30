


function clearColor()
{
$('#colid').val('');
$('#collection_name').val('');
}

function loadCollection()
{
	$('#dataTables-example2').DataTable().clear();
	var editButtonHtml = "<a class=\"btn btn-primary btn-sm\" id=\"btnEditColl\" data-toggle=\"modal\" data-target=\"#myModal2\"  ><i class=\"fa fa-edit \"></i> Edit</a>";
	var deleteButtonHtml = "<a class=\"btn btn-danger btn-sm\" id=\"btnDeleteColl\" ><i class=\"fa fa-trash-o \"></i> Delete</a>";
	$.ajax({
		type: "GET",
		url: baseUrl + getAllCollections,
		data: { data: "" },
		cache: false,
		success: function (result) {
			data = JSON.parse(JSON.stringify(result.data));
			console.log(result);
			if (result.status == 'OK') {
				$.each(result.data, function (index, data) {
					var rowIndex = $('#dataTables-example2').dataTable().fnAddData([
						data.collection_name,
						editButtonHtml + "  " + deleteButtonHtml]
					);
					var row = $('#dataTables-example2').dataTable().fnGetNodes(rowIndex);
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

$(document).on("click", "#btnAddColl", function (event) {
	event.preventDefault();
	if (($('#collection_name').val() != '')){
		if($('#colid').val !='')
		{
			var collection = {
					"id":$('#colid').val(),
					"collection_name": $('#collection_name').val()};
			saveCollection(collection);
			
		}
			
	}
	else {
		if ($('#collection_name').val() == '') {
			$('#collection_name').addClass("field-red");
			$('#collection_name').removeClass("field-ok");
		}
		else {
			$('#collection_name').addClass("field-ok");
			$('#collection_name').removeClass("field-red");
		}
		
	}

});



function saveCollection(collection) {
	$.ajax({
		type: "POST",
		headers: {
			'content-type': 'application/json'
		},
		url: baseUrl + addCollection,
		dataType: "json",
		data:  JSON.stringify(collection),
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
				    text: "Collection Operation Success",
				    hideAfter: 2000,
				    icon: 'success',
				    loader: false,
				    showHideTransition: 'fade',
				    stack: false,
				    position: 'top-left'
  	
				});
				$('.btn-default').click();
				$('.close').click();
				loadCollection();
				
				
			}
			else {
				$.toast({
				    text: "Unable to do Collection Operation",
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

$(document).on("click", "#btnEditColl", function () {
	var pid = $(this).closest('tr').attr('id');
	var row = $(this).closest('tr');
	$('#colid').val(pid);
	$('#collection_name').val(row.find("td:eq(0)").text());
});


$(document).on("click", "#btnDeleteColl", function () {
	var pid = $(this).closest('tr').attr('id');
	deleteCollectionbyId(pid);
});

function deleteCollectionbyId(pid) {
	$.ajax({
		type: "DELETE",
		headers: {
			'content-type': 'application/json'
		},
		url: baseUrl + deleteCollection+"/"+pid,
		crossDomain: true,
		async: true,
		timeout: 60000,
		success: function (data) {
			console.log("SUCCESS : ", data);
			if (data.status == 'OK') {
				$.toast({
				    text: "Collection Deleted Successfully",
				    hideAfter: 2000,
				    icon: 'success',
				    loader: false,
				    showHideTransition: 'fade',
				    stack: false,
				    position: 'top-left'
  	
				});
				loadCollection();
				
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



