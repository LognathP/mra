function openCollection()
{
	loadCollection();
}

function openPattern()
{
	loadPattern();
}

function openColor()
{
	loadColor();
}

function openProduct()
{
	loadProducts();
}

function openSubCategory()
{
	loadSubCategory();
}

$(document).ready(function() {
	if(localStorage.getItem("categoryArray") == null)
	{
		getListCategory();
	}

   
});

function getListCategory()
{
	$.ajax({
		type: "GET",
		url: baseUrl + getAllCategories,
		cache: false,
		success: function (catresult) {
			//console.log(result);
			var catArray = [];
			if (catresult.status == 'OK') {
				
				$.each(catresult.data, function (index, data) {
					itemCat = {}
					itemCat ["id"] = data.id;
					itemCat["text"] = data.category_name;
					catArray.push(itemCat);
				});
				localStorage.setItem("categoryArray",JSON.stringify(catArray));
				if(localStorage.getItem("collectionArray") == null)
				{
					getListCollection();
				}
				
			   
				 
		}
			else {
				$.toast({
				    text: "Unable to Load Category",
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



function getListCollection()
{
	$.ajax({
		type: "GET",
		url: baseUrl + getAllCollections,
		cache: false,
		success: function (colresult) {
			console.log(colresult);
			var colArray = [];
			if (colresult.status == 'OK') {
				
				$.each(colresult.data, function (index, data) {
					itemCol = {}
					itemCol ["id"] = data.id;
					itemCol["text"] = data.collection_name;
					colArray.push(itemCol);
				});
				localStorage.setItem("collectionArray",JSON.stringify(colArray));
				if(localStorage.getItem("colorArray") == null)
				{
					getListColor();
				}
				
				   
				 
		}else {
			$.toast({
			    text: "Unable to Load Collection",
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

function getListColor()
{
	$.ajax({
		type: "GET",
		url: baseUrl + getAllColors,
		cache: false,
		success: function (clresult) {
			console.log(clresult);
			var clArray = [];
			if (clresult.status == 'OK') {
				$.each(clresult.data, function (index, data) {
					itemCl = {}
					itemCl ["id"] = data.id;
					itemCl["text"] = data.color_name;
					clArray.push(itemCl);
				});
				localStorage.setItem("colorArray",JSON.stringify(clArray));
				if(localStorage.getItem("patternArray") == null)
				{
					getListPattern();
				}
				
				 
		}else {
			$.toast({
			    text: "Unable to Load Color",
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

function getListPattern()
{
	$.ajax({
		type: "GET",
		url: baseUrl + getAllPatterns,
		cache: false,
		success: function (presult) {
			console.log(presult);
			var patArray = [];
			if (presult.status == 'OK') {
				$.each(presult.data, function (index, data) {
					itempat = {}
					itempat ["id"] = data.id;
					itempat["text"] = data.name;
					patArray.push(itempat);
				});
				localStorage.setItem("patternArray",JSON.stringify(patArray));

				 
		}else {
			$.toast({
			    text: "Unable to Load Pattern",
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


	