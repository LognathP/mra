var editFlag = false;
var editId = 0;
$(document).ready(function() {
	if(localStorage.getItem("editProdId") != null)
	{
		editFlag = true;
		editId = localStorage.getItem("editProdId");
		getProductById(editId);
	}
		
	$('.category-multiple').select2({
	   	placeholder: "Select Category",
	   	tokenSeparators: [','],
		data: JSON.parse(localStorage.getItem("categoryArray"))
    });
	$('.collection-multiple').select2({
	   	placeholder: "Select Collection",
	   	tokenSeparators: [','],
	   	data: JSON.parse(localStorage.getItem("collectionArray"))
    });
	$('.color-multiple').select2({
	   	placeholder: "Select Color",
	   	tokenSeparators: [','],
	   	data: JSON.parse(localStorage.getItem("colorArray"))
    });
	$('.pattern-multiple').select2({
	   	placeholder: "Select Pattern",
	   	tokenSeparators: [','],
	   	data: JSON.parse(localStorage.getItem("patternArray"))
    });
   
});


$(document).on("click", "#btnAddProd", function (event) {
	event.preventDefault();
	
	if(validateInputs())
	{		var product = {
				"product_name":$('#product_name').val(),
				"product_desc":$('#product_desc').val(),
				"colors":$('#product_col').val().join(","),
				"patterns":$('#product_pat').val().join(","),
				"price":parseInt($('#price').val()),
				"selling_price":parseInt($('#selling_price').val()),
				"units":parseInt($('#units').val()),
				"collection":$('#product_coll').val().join(","),
				"category":$('#product_cat').val().join(",")
				
		};
	//console.log(product);
		 addProduct(product);
		
	}

});

function validateInputs()
{
	var all = 0;
	var s = 0;
	var e = 0;
	$('input').each(function() {
		all++;
		
		if ($(this).val().length>0) {
			$('#'+$(this).attr('id')).removeClass("field-red");
			$('#'+$(this).attr('id')).addClass("field-ok");
			s++;
		} else {
			if(editFlag)
			{
				if($(this).attr('id') == 'product_file')
				{
					s++;
				}
			}
			else
			{
				$('#'+$(this).attr('id')).removeClass("field-ok");
				$('#'+$(this).attr('id')).addClass("field-red");
				e++;
			}
			
		}
		
	});
	$('select').each(function() {
		all++;
		if ($(this).val() == null) {
			$.toast({
			    text: "Please select values in the empty fields",
			    hideAfter: 2000,
			    icon: 'error',
			    loader: false,
			    showHideTransition: 'fade',
			    stack: false,
			    position: 'top-left'
	
			});
			e++
		}
		else
		{ s++;}
	});
	
	if(all == s)
	{return true;}
	else
	{return false;}
}






function setError(id) {
	$('#'+id).addClass("field-red");
	$('#'+id).removeClass("field-ok");
}
function setClear(id) {
	$('#'+id).addClass("field-ok");
	$('#'+id).removeClass("field-red");
}

function addProduct(product) {
	 var file = $('#productUpload')[0];
	 var form = new FormData(file);
	 form.append("product_name",product.product_name);
	 form.append("product_desc",product.product_desc);
	 form.append("colors",product.colors);
	 form.append("patterns",product.patterns);
	 form.append("category",product.category);
	 form.append("price",product.price);
	 form.append("selling_price",product.selling_price);
	 form.append("units",product.units);
	 form.append("collection",product.collection);
	 if(editFlag)
	 {
		 form.append("id",editId);
	 }
	
	 console.log(form);
	$.ajax({
		type: "POST",
		url: baseUrl + addproductApi,
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
				    text: "Product Opertion Success",
				    hideAfter: 2000,
				    icon: 'success',
				    loader: false,
				    showHideTransition: 'fade',
				    stack: false,
				    position: 'top-left'
  	
				});
				location.href = 'product.html';
				localStorage.removeItem("editProdId");
				editFlag = false;
			}
			else {
				$.toast({
					text: "Unable to do Product Operation",
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

function getProductById(pid) {
	$.ajax({
		type: "GET",
		headers: {
			'content-type': 'application/json'
		},
		url: baseUrl + getProductByIdApi+"/"+pid,
		crossDomain: true,
		async: true,
		timeout: 60000,
		success: function (data) {
			console.log("SUCCESS : ", data);
			if (data.status == 'OK') {
				console.log(data);
				$('#product_name').val(data.data.product_name);
				$('#product_desc').val(data.data.product_desc);
				$('#price').val(data.data.price);
				$('#selling_price').val(data.data.selling_price);
				$('#units').val(data.data.units);
				$('.category-multiple').val(JSON.parse(data.data.category)).trigger("change");
				$('.collection-multiple').val(JSON.parse(data.data.collection)).trigger("change");
				$('.color-multiple').val(JSON.parse(data.data.colors)).trigger("change");
				$('.pattern-multiple').val(JSON.parse(data.data.patterns)).trigger("change");
			}
			else {
				$.toast({
				    text: "Unable to Load Product",
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
