var unit_catg;
//$('#dataTables-example').DataTable();
var modalHtml1 = $("#myModal1").html();
$(document).ready(function () {

	loadSubProduct();
	
});
var spid;


function loadSubProduct()
{
	$('#dataTables-example1').DataTable().clear();
	var editButtonHtml = "<a class=\"btn btn-primary btn-sm\" id=\"btnSubEdit\" data-toggle=\"modal\" data-target=\"#myModal1\"  ><i class=\"fa fa-edit \"></i> Edit</a>";
	var deleteButtonHtml = "<a class=\"btn btn-danger btn-sm\" id=\"btnSubDelete\" ><i class=\"fa fa-trash-o \"></i> Delete</a>";
	var viewButtonHtml = "<a class=\"btn btn-success btn-sm\" id=\"btnViewPrice\" >View Price</a>";

	$.ajax({
		type: "POST",
		url: baseUrl + listsubproductApi,
		data: { data: "" },
		cache: false,
		success: function (result) {
			data = JSON.parse(JSON.stringify(result.data));
			if (result.status == '200 OK') {
				$.each(data, function (index, data) {
					var rowIndex = $('#dataTables-example1').dataTable().fnAddData([
						data.product_name,
						data.sub_product_name,
						data.sub_product_desc,
						data.unit_metrics == 1 ?'Kg':'Nos',
						viewButtonHtml,
						editButtonHtml + "  " + deleteButtonHtml]
					);
					var row = $('#dataTables-example1').dataTable().fnGetNodes(rowIndex);
					$(row).attr('id', data.sub_product_id);
				});			
			}
			else if (data.status == '403 Forbidden'){
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
$(document).on('change','#prod_unit',function(){
	unit_catg = $(this).val();
	if(unit_catg == 1)
		{
		$('#divkg').show();
		$('#divnos').hide();
		}
	else
		{
		$('#divkg').hide();
		$('#divnos').show();
		}
});



$(document).on("click", "#subProdTab", function (event) {
	listProducts();
});

$(document).on("click", "#subprod", function (event) {
	$("#myModal1").html(modalHtml1);
	$('#subproductEdit').hide();
	$('#subproductAdd').show();
	listProducts();
});

function listProducts()
{
	$.ajax({
		type: "POST",
		url: baseUrl + listproductApi,
		data: { data: "" },
		cache: false,
		success: function (result) {
			data = JSON.parse(JSON.stringify(result.data));
			if (result.status == '200 OK') {
				$('#prod_list').empty();
				$('#prod_list').append("<option value=\"\" disabled selected>Product</option>");
				$.each(data, function () {
					$('#prod_list').append("<option value=" + this.product_id +">" + this.product_name + "</option>");
				});
			
	}
			else if (data.status == '403 Forbidden'){
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

$(document).on("click", "#btnUpdSub", function (event) {
	event.preventDefault();
	var adEditFlag = 1;
	var catg = parseInt($("#prod_unit").val());

	if(catg == 1)
		{
		validateAndAddKg(adEditFlag);
		}
	else
		{
		validateAndAddNos(adEditFlag);
		}

});

$(document).on("click", "#btnAddSub", function (event) {
	event.preventDefault();
	var catg = parseInt($("#prod_unit").val());
	var adEditFlag = 0;

	if(catg == 1)
		{
		validateAndAddKg(adEditFlag);
		}
	else
		{
		validateAndAddNos(adEditFlag);
		}

});

function validateAndAddNos(adEditFlag)
{
	if (($('#subprod_desc').val() != '') && ($('#subprod_name').val() != '') 
			&& ($('#subprod_name').val() != '') 
			&& ($('#price').val() != '') && ($('#prod_list').val() != null) && ($('#prod_unit').val() != null)){
		
		if(adEditFlag == 0)
			{
			var SubProduct = {
					"sub_product_name": $('#subprod_name').val(),
					"sub_product_desc": $('#subprod_desc').val(),
					"sub_prod_price1": parseFloat($('#price').val()),
					"unit_metrics":parseInt($("#prod_unit").val()), 
					"product_id": $('#prod_list').val()

				};			
			}
		else
			{
			var SubProduct = {
					"sub_product_id": parseInt(spid),
					"sub_product_name": $('#subprod_name').val(),
					"sub_product_desc": $('#subprod_desc').val(),
					"sub_prod_price1": parseFloat($('#price').val()),
					"unit_metrics":parseInt($("#prod_unit").val()), 
					"product_id": $('#prod_list').val()

				};
			}

			console.log(SubProduct);		
		saveSubProductApiCall(SubProduct,adEditFlag);
	}
	else {
		if ($('#subprod_desc').val() == '') {
			setError('subprod_desc');
		}
		else {
			setClear('subprod_desc');
		}
		if ($('#subprod_name').val() == '') {
			setError('subprod_name');
		}
		else {
			setClear('subprod_name');
		}
		if ($('#price').val() == '') {
			setError('price');
		}
		else {
			setClear('price');
		}
		if ($('#prod_list').val() == null) {
			setError('prod_list');
		}
		else {
			setClear('prod_list');
		}
		if ($('#prod_unit').val() == null) {
			setError('prod_unit');
		}
		else {
			setClear('prod_unit');
		}
	}
	
}

function validateAndAddKg(adEditFlag)
{
	if (($('#subprod_desc').val() != '') && ($('#subprod_name').val() != '') 
			&& ($('#subprod_name').val() != '') 
			&& ($('#price1').val() != '')&& ($('#price2').val() != '')
			&& ($('#price4').val() != '')&& ($('#price3').val() != '')
			&& ($('#price5').val() != '')&& ($('#prod_list').val() != null) && ($('#prod_unit').val() != null)){

		if(adEditFlag == 0)
		{
			var SubProduct = {
					"sub_product_name": $('#subprod_name').val(),
					"sub_product_desc": $('#subprod_desc').val(),
					"sub_prod_price1": parseFloat($('#price1').val()),
					"sub_prod_price2": parseFloat($('#price2').val()),
					"sub_prod_price3": parseFloat($('#price3').val()),
					"sub_prod_price4": parseFloat($('#price4').val()),
					"sub_prod_price5": parseFloat($('#price5').val()),
					"unit_metrics":parseInt($("#prod_unit").val()), 
					"product_id": $('#prod_list').val()

				};			
		}
	else
		{
		var SubProduct = {
				"sub_product_id": parseInt(spid),
				"sub_product_name": $('#subprod_name').val(),
				"sub_product_desc": $('#subprod_desc').val(),
				"sub_prod_price1": parseFloat($('#price1').val()),
				"sub_prod_price2": parseFloat($('#price2').val()),
				"sub_prod_price3": parseFloat($('#price3').val()),
				"sub_prod_price4": parseFloat($('#price4').val()),
				"sub_prod_price5": parseFloat($('#price5').val()),
				"unit_metrics":parseInt($("#prod_unit").val()), 
				"product_id": $('#prod_list').val()

			};	
		}
		console.log(SubProduct);		
		saveSubProductApiCall(SubProduct,adEditFlag);
	}
	else {
		if ($('#subprod_desc').val() == '') {
			setError('subprod_desc');
		}
		else {
			setClear('subprod_desc');
		}
		if ($('#subprod_name').val() == '') {
			setError('subprod_name');
		}
		else {
			setClear('subprod_name');
		}
		if ($('#price1').val() == '') {
			setError('price1');
		}
		else {
			setClear('price1');
		}
		if ($('#price2').val() == '') {
			setError('price2');
		}
		else {
			setClear('price2');
		}
		if ($('#price3').val() == '') {
			setError('price3');
		}
		else {
			setClear('price3');
		}
		if ($('#price4').val() == '') {
			setError('price4');
		}
		else {
			setClear('price4');
		}
		if ($('#price5').val() == '') {
			setError('price5');
		}
		else {
			setClear('price5');
		}
		if ($('#prod_list').val() == null) {
			setError('prod_list');
		}
		else {
			setClear('prod_list');
		}
		if ($('#prod_unit').val() == null) {
			setError('prod_unit');
		}
		else {
			setClear('prod_unit');
		}
	}
	
}

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


function saveSubProductApiCall(SubProduct,opFlag) {
	//var token = sessionStorage.getItem("Authorization");
	var req = JSON.stringify(SubProduct);
	if(opFlag == 0)
		{
		var api = addsubproductApi;
	}else
		{
		var api = editsubproductApi;

		}
	$.ajax({
		type: "POST",
		headers: {
			// 'Authorization': token,
			'content-type': 'application/json'
		},
		url: baseUrl + api,
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
				$('.btn-default').click();
				$('.close').click();
				loadSubProduct();
				
				
			}
			else if (data.status == '403 Forbidden'){
				$.toast({
				    text: data.message,
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

$(document).on("click", "#btnSubEdit", function () {
	$('#subproductAdd').hide();
	$('#subproductEdit').show();
	spid = $(this).closest('tr').attr('id');
	var wc = $(this).closest('tr').find("td:eq(3)").text()=='Kg'?1:2;
	$('#spid').val(spid);
	var op = "edit";
	listSubProductPriceApi(spid,wc,op);
	
});

$(document).on("click", "#btnViewPrice", function (event) {

	spid = $(this).closest('tr').attr('id');
	var wc = $(this).closest('tr').find("td:eq(3)").text()=='Kg'?1:2;
	console.log(spid);
	var op = "price";
	listSubProductPriceApi(spid,wc,op);
	
});


$(document).on("click", "#btnSubDelete", function () {
	var spid = $(this).closest('tr').attr('id');
	deleteSubProductApiCall(spid);
});

function deleteSubProductApiCall(spid) {
	var req = { 'sub_product_id': parseInt(spid) };
	req = JSON.stringify(req);
	console.log(req);
	$.ajax({
		type: "POST",
		headers: {
			// 'Authorization': token,
			'content-type': 'application/json'
		},
		url: baseUrl + deletesubproductApi,
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
				loadSubProduct();
				
			}
			else if (data.status == '403 Forbidden'){
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


function listSubProductPriceApi(spid,wc,op) {
	var req = { 'sub_product_id': spid };
	req = JSON.stringify(req);

	$.ajax({
		type: "POST",
		headers: {
			// 'Authorization': token,
			'content-type': 'application/json'
		},
		url: baseUrl + getsubproductApi,
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
				var tr;
				var th;
				var response = JSON.parse(JSON.stringify(data.data));
			
				if(op == 'edit')
					{
					if(wc == 1)
					{
					$('#divkg').show();
					$('#divnos').hide();
					$('#price1').val(response.sub_prod_price1);
					$('#price2').val(response.sub_prod_price2);
					$('#price3').val(response.sub_prod_price3);
					$('#price4').val(response.sub_prod_price4);
					$('#price5').val(response.sub_prod_price5);
					$('#prod_list').val(response.product_id);	
					$('#prod_unit').val(response.unit_metrics);	
					$('#subprod_desc').val(response.sub_product_desc);
					$('#subprod_name').val(response.sub_product_name);
					}
				else
					{
					$('#divkg').hide();
					$('#divnos').show();
					$('#price').val(response.sub_prod_price1);
					$('#prod_list').val(response.product_id);
					$('#prod_unit').val(response.unit_metrics);	
					$('#subprod_desc').val(response.sub_product_desc);
					$('#subprod_name').val(response.sub_product_name);
					}
				
				}
				else
					{
					if(wc == 1)
					{
					th = th + "<tr><th>Weight Category</th><th>Price</th></tr>"
					tr = tr + "<tr><td>25 - 250 Kg</td><td>"+"&#x20B9; "+ response.sub_prod_price1 + "</td></tr>";
					tr = tr + "<tr><td>250 - 500 Kg</td><td>"+"&#x20B9; " + response.sub_prod_price2 + "</td></tr>";
					tr = tr + "<tr><td>500 - 750 Kg</td><td>"+"&#x20B9; " + response.sub_prod_price3 + "</td></tr>";
					tr = tr + "<tr><td>750 - 1000 Kg</td><td>"+"&#x20B9; " + response.sub_prod_price4 + "</td></tr>";
					tr = tr + "<tr><td>1000+ Kg</td><td>"+"&#x20B9; " + response.sub_prod_price5 + "</td></tr>";
					}
				else
					{
					th = th + "<tr><th>Quantity</th><th>Price</th></tr>"
					tr = tr + "<tr><td>1 Nos</td><td>"+"&#x20B9; "+ response.sub_prod_price1 + "</td></tr>";
					}
				
				$('#priceTable thead').html(th);
				$('#priceTable tbody').html(tr);
				$('#myModalViewPrice').modal('show');
					}
				
			}
			else if (data.status == '403 Forbidden'){
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
