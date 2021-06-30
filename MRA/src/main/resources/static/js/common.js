

function loadData()
{
	getCategory();
	getCollection();
	getColor();
	getPattern();
	}

function getCategory()
{
	$.ajax({
		type: "GET",
		url: baseUrl + getAllCategories,
		cache: false,
		success: function (catresult) {
			var catArray = [];
			if (catresult.status == 'OK') {
				
				$.each(catresult.data, function (index, data) {
					itemCat = {}
					itemCat ["id"] = data.id;
					itemCat["text"] = data.category_name;
					catArray.push(itemCat);
				});
				
			   
				 
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

function getCollection()
{
	$.ajax({
		type: "GET",
		url: baseUrl + getAllCollections,
		cache: false,
		success: function (colresult) {
			console.log(colresult);
			
			if (colresult.status == 'OK') {
				
				$.each(colresult.data, function (index, data) {
					itemCol = {}
					itemCol ["id"] = data.id;
					itemCol["text"] = data.collection_name;
					colArray.push(itemCol);
				});
				
				   
				 
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

function getColor()
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

function getPattern()
{
	$.ajax({
		type: "GET",
		url: baseUrl + getAllPatterns,
		cache: false,
		success: function (presult) {
			var patArray = [];
			if (presult.status == 'OK') {
				$.each(presult.data, function (index, data) {
					itempat = {}
					itempat ["id"] = data.id;
					itempat["text"] = data.pattern_name;
					patArray.push(itempat);
				});
				 
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
