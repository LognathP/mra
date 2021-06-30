var getUrl = window.location;
var baseUrl = getUrl.protocol + "//" + getUrl.host + "/" + getUrl.pathname.split('/')[1];

//Category API
var addCategory = "/api/category/add";
var editCategory = "/api/category/edit";
var deleteCategory = "/api/category/delete";
var getAllCategories = "/api/category/getall";
var getCategory = "/api/category/get";

//Collection API
var addCollection = "/api/collection/add";
var editCollection = "/api/collection/edit";
var deleteCollection = "/api/collection/delete";
var getAllCollections = "/api/collection/getall";
var getCollection = "/api/collection/get";

//Pattern API
var addPattern = "/api/pattern/add";
var deletePattern = "/api/pattern/delete";
var getAllPatterns = "/api/pattern/getall";
var getPattern = "/api/pattern/get";


var addColor = "/api/color/add";
var deleteColor = "/api/color/delete";
var getAllColors = "/api/color/getall";
var getColor = "/api/color/get";


//Product API
var addproductApi = "/api/product/add";
var editproductApi = "/api/product/edit";
var deleteproductApi = "/api/product/delete";
var listproductApi = "/api/product/getall";


//Orders API
var getOrdersApi = "/api/order/list";
var getOrderItemsApi = "/api/order/items";
var updateOrderApi = "/api/order/update";
var getOrderDetailApi = "/api/order/detail";
var getOrderInvoiceApi = "/api/order/invoice";




