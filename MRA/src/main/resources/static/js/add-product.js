$(document).ready(function() {
	$('.category-multiple').select2({
    	//maximumSelectionLength: 2,
	   	placeholder: "Select Category",
	   	tokenSeparators: [','],
	   	data: catArray
    });
	$('.collection-multiple').select2({
    	//maximumSelectionLength: 2,
	   	placeholder: "Select Collection",
	   	tokenSeparators: [','],
	   	data: colArray
    });
	$('.color-multiple').select2({
    	//maximumSelectionLength: 2,
	   	placeholder: "Select Colors",
	   	tokenSeparators: [','],
	   	data: clArray
    });
	$('.pattern-multiple').select2({
    	//maximumSelectionLength: 2,
	   	placeholder: "Select Pattern",
	   	tokenSeparators: [','],
	   	data: patArray
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
			$('#'+$(this).attr('id')).removeClass("field-ok");
			$('#'+$(this).attr('id')).addClass("field-red");
			e++;
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
