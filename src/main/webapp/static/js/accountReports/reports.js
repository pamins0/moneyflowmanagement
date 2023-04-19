

$('#datetimepicker1').datetimepicker();	
$('#datetimepicker2').datetimepicker({defaultDate : new Date()});

$(function () {
    $('#commSumReportGraphDiv').highcharts({
        data: {
            table: 'commSumReportGraphTable'
        },
        chart: {
            type: 'column'
        },
        title: {
            text: 'Graphical Representation of Commission Records.'
        },             
        xAxis: {
        	 allowDecimals: false,
             title: {
                 text: ''
             }
        },
        yAxis: {
            allowDecimals: true,
            title: {
                text: 'Total Amount (RM)'
            }
        },  
        tooltip: {
            formatter: function () {
                return '<b>' + this.series.name + '</b><br/>' +
                    this.point.y + ' ' + this.point.name.toLowerCase();
            }
        },
        plotOptions: {
            series: {
            	 dataLabels: {
                     enabled: true,
                     //rotation: -60,
                     color: '#FFFFFF',
                     align: 'right',
                     format: '{point.y:.1f} RM', // one decimal
                     y: 10, // 10 pixels down from the top
                     style: {
                         fontSize: '10px',
                         fontFamily: 'Verdana, sans-serif'
                     }
                 }
            }
        },
    });
});