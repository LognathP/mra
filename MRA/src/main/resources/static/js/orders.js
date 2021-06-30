$('#dataTables-example').DataTable();
$(document).ready(function () {

	loadUserType();

});


function loadUserType()
{
		$.ajax({
		type: "POST",
		url: baseUrl + listUserTypeApi,
		data: { data: "" },
		cache: false,
		success: function (result) {
			data = JSON.parse(JSON.stringify(result.data));
			if (result.status == '200 OK') {
				$('#user_type').empty();
				$('#user_type').append("<option value=\"\" disabled selected>User Type</option>");
				$('#user_type').append("<option value=\"0\">All</option>");
				$.each(data, function () {
					$('#user_type').append("<option value=" + this.user_type_id + ">" + this.user_type_name + "</option>");
				});
			}
			else {
				$.toast({
				    text: result.message,
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



$(document).on('click','#get_order',function(){
	
	if (($('#from_date').val() != '') && ($('#to_date').val() != '') && ($('#user_type').val() != null)
			){

		var Order = {
			"from_date": $('#from_date').val(),
			"to_date": $('#to_date').val(),
			//"order_status":$('#order_status').val(),
			"order_user_type":$('#user_type').val()};
		console.log(Order);
		loadOrders(Order);
	}
	else {
		if ($('#from_date').val() == '') {
			setError('from_date');
		}
		else {
			setClear('from_date');
		}
		if ($('#to_date').val() == '') {
			setError('to_date');
		}
		else {
			setClear('to_date');
		}
		
		if ($('#user_type').val() == null) {
			setError('user_type');
		}
		else {
			setClear('user_type');
		}
	}

});

$(document).on('click','#download_order',function(){
	
	if (($('#from_date').val() != '') && ($('#to_date').val() != '')){
			var req = new XMLHttpRequest();
		    req.open("GET", baseUrl + getOrderInvoiceApi+"/?fromDate="+$('#from_date').val()+"&toDate="+$('#to_date').val(), true);
			req.responseType = "blob";
			req.onload = function (event) {
			    var blob = req.response;
			    console.log(blob.size);
			    var link=document.createElement('a');
			    link.href=window.URL.createObjectURL(blob);
			    link.download="customers"+".csv";
			    link.click();
			  };
			  req.send();
	}
	else {
		if ($('#from_date').val() == '') {
			setError('from_date');
		}
		else {
			setClear('from_date');
		}
		if ($('#to_date').val() == '') {
			setError('to_date');
		}
		else {
			setClear('to_date');
		}
		
		if ($('#user_type').val() == null) {
			setError('user_type');
		}
		else {
			setClear('user_type');
		}
	}

});

function setError(id) {
	$('#' + id).css("border-color", "#ff383b");
	$('#' + id).css("box-shadow", "0 1px 1px rgba(0, 0, 0, 0.075) inset, 0 0 8px rgba(232, 88, 88, 0.6)");
	$('#' + id).css("outline", "0 none");
}
function setClear(id) {
	$('#' + id).css("border-color", "#66afe9");
	$('#' + id).css("box-shadow", "0 1px 1px rgba(0, 0, 0, 0.075) inset, 0 0 8px rgba(102, 175, 233, 0.6)");
	$('#' + id).css("outline", "0 none");
}

function loadOrders(order)
{
	var req = JSON.stringify(order);
	$('#dataTables-example').DataTable().clear();
	var status;
	var badge;
	var viewButtonHtml = "<a class=\"btn btn-light btn-sm\" data-toggle=\"modal\" data-target=\"#myModalView\">View Details</a>";
	var icon = "";
	$.ajax({
		type: "POST",
		headers: {
			'content-type': 'application/json'
		},
		url: baseUrl + getOrdersApi,
		data: req,
		dataType: "json",
		data: req,
		processData: false,
		contentType: false,
		crossDomain: true,
		async: true,
		cache: false,
		timeout: 60000,
		success: function (result) {
			data = JSON.parse(JSON.stringify(result.data));
			$('#empty').hide();
			console.log(data);
			if (result.status == '200 OK') {
				$.each(data, function (index, data) {
					if(data.orders_type == 3 || data.orders_type == 2 )
						{
						icon = "<i class=\"fa fa-wrench\" aria-hidden=\"true\"></i>";
						}
					else
						{
						icon = "<i class=\"fa fa-recycle\" aria-hidden=\"true\"></i>";
						}
					if(data.order_status == 'P')
						{
						status = "Processing";
						badge = "label label-warning";
						}
					else if (data.order_status == 'F')
						{
						status = "Completed";
						badge = "label label-success";
						}
					else if (data.order_status == 'C')
						{
						status = "Cancelled";
						badge = "label label-danger";
						}
					var rowIndex = $('#dataTables-example').dataTable().fnAddData([
						data.order_customer_name,
						data.order_id,
						data.order_contact_number,
						data.order_address,
						data.order_pincode,
						icon + "  " +viewButtonHtml,
						"<label name=\""+data.order_status+"\" class=\""+badge+"\" >" + status + "</label>"]
					);
					
						$(row).attr('class', 'primary');
					var row = $('#dataTables-example').dataTable().fnGetNodes(rowIndex);
					$(row).attr('id', data.order_id);
				
				});
					
				$('#tab').show();
			}
			else {
				$.toast({
				    text: result.message,
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


$(document).on('click','.btn-sm',function(){
	
	var orderID = $(this).closest('tr').attr('id');
	$('#order_id').val(orderID);
	var Order = {
			"order_id": orderID};
		loadOrderItems(Order);
		loadOrderDetail(Order);
	
});

$(document).on('click','#update_order',function(){
	
	var Order = {
			"order_id" : $('#order_id').val(),
			"order_price" : $('#order_price').val(),
			"order_status" : $('#order_status').val()};
		updateOrder(Order);
	
});


function loadOrderItems(order)
{
	var req = JSON.stringify(order);
	$.ajax({
		type: "POST",
		headers: {
			'content-type': 'application/json'
		},
		url: baseUrl + getOrderItemsApi,
		data: req,
		dataType: "json",
		data: req,
		processData: false,
		contentType: false,
		crossDomain: true,
		async: true,
		cache: false,
		timeout: 60000,
		success: function (result) {
			console.log(result);
			data = JSON.parse(JSON.stringify(result.data));
			console.log(data);
			$('#itemsTable tbody').html('');
			$('#itemsTable thead').html('');

			if (result.status == '200 OK') {
				var th = "";
				var tr = "";
				th = th + "<tr><th>Sub Product</th><th>Quantity</th></tr>"
				
				$.each(data, function (index, value) {
					var unit = value.order_unit == 1 ? 'kg' : 'Nos';
					var orderQuantity = value.order_quantity+" "+unit;
					console.log(orderQuantity);
				tr = tr + "<tr><td>"+value.sub_product_name+"</td><td>"+orderQuantity+"</td></tr>";
					
				});
			$('#itemsTable tbody').append(tr);
			$('#itemsTable thead').html(th);
			$('#myModalViewItems').modal('show');
			}
			else {
				$.toast({
				    text: result.message,
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

function loadOrderDetail(order)
{
	var req = JSON.stringify(order);
	$.ajax({
		type: "POST",
		headers: {
			'content-type': 'application/json'
		},
		url: baseUrl + getOrderDetailApi,
		data: req,
		dataType: "json",
		data: req,
		processData: false,
		contentType: false,
		crossDomain: true,
		async: true,
		cache: false,
		timeout: 60000,
		success: function (result) {
			console.log(result);
			data = JSON.parse(JSON.stringify(result.data));
			console.log(data);
			if (result.status == '200 OK') {
				$('#order_price').val(data.order_price);
				$('#order_status').val(data.order_status);
				if(data.order_status == 'C')
					{
					  $('#order_status').prop('disabled',true);
					  $('#order_price').prop('readonly',true);
					  $('#update_order').prop('disabled',true);
					}
			}
			else {
				$.toast({
				    text: result.message,
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


function updateOrder(order) {
	var req = JSON.stringify(order);
	$.ajax({
		type: "POST",
		headers: {
			'content-type': 'application/json'
		},
		url: baseUrl + updateOrderApi,
		dataType: "json",
		data: req,
		processData: false,
		contentType: false,
		crossDomain: true,
		async: true,
		cache: false,
		timeout: 60000,
		success: function (data) {

			console.log("SUCCESS : ", data);
			if (data.status == '200 OK') {
				$.toast({
				    text: data.message,
				    hideAfter: 2000,
				    icon: 'success',
				    loader: false,
				    showHideTransition: 'fade',
				    stack: false,
				    position: 'top-left'
  	
				});
				$('.close').click();
				
			}
			else {
				$.toast({
				    text: data.message,
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


