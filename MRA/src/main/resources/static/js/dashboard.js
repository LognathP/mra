	$.fn.jQuerySimpleCounter = function( options ) {
	    var settings = $.extend({
	        start:  0,
	        end:    100,
	        easing: 'swing',
	        duration: 400,
	        complete: ''
	    }, options );

	    var thisElement = $(this);

	    $({count: settings.start}).animate({count: settings.end}, {
			duration: settings.duration,
			easing: settings.easing,
			step: function() {
				var mathCount = Math.ceil(this.count);
				thisElement.text(mathCount);
			},
			complete: settings.complete
		});
	};

$('#number1').jQuerySimpleCounter({end: 36,duration: 2500});
$('#number2').jQuerySimpleCounter({end: 150,duration: 2500});
$('#number3').jQuerySimpleCounter({end: 49,duration: 2000});
$('#number4').jQuerySimpleCounter({end: 101,duration: 2500});

	$(document).on(function(){
		 $('[data-toggle="tooltip"]').tooltip()
		 });