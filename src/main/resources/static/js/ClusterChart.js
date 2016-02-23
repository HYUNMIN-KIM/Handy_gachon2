function drawChart() {
	
	chart = new AmCharts.AmSerialChart();
	chart.dataProvider = data;
	chart.dataDateFormat = "YYYY/MM/DD";
	chart.categoryField = "date";
	chart.creditsPosition = "top-left";

	// LEGEND
	var legend = new AmCharts.AmLegend();
	legend.useGraphSettings = true;
	chart.addLegend(legend);

	// valueAxis
	// chart
	var valueAxis = new AmCharts.ValueAxis();
	valueAxis.title = "Cluster Type"
	valueAxis.tickLength = 0;
	valueAxis.axisAlpha = 0;
	valueAxis.minimum = 0;
	valueAxis.gridCount = 5;
	valueAxis.dashLength = 3;
	valueAxis.showFirstLabel = false;
	valueAxis.showLastLabel = false;
	valueAxis.position = "left";
	chart.addValueAxis(valueAxis);

	var graph_cluster = new AmCharts.AmSerialChart();
	graph_cluster.type = "column";
	graph_cluster.title = "Cluster";
	graph_cluster.bullet = "round";
	//graph_cluster.dashLength = 1;
	graph_cluster.lineThickness = 2.0;
	graph_cluster.lineColor = "#85C5E3";
	graph_cluster.valueField = "type";
	graph_cluster.balloonText = "<span style='font-size:14px;'><b>[[type]] Cluster</b>" +
			"<br>TI : [[ti]] PI : [[pi]] SI : [[si]]<br>TVI : [[tvi]] PVI : [[pvi]] AI : [[ai]]</span>";
	graph_cluster.valueAxis = valueAxis;
	graph_cluster.fillAlphas = 0.1;
	chart.addGraph(graph_cluster);
	
	// categoryAxis
	var categoryAxis = chart.categoryAxis;
	categoryAxis.parseDates = true;
	categoryAxis.minPeriod = "DD";
	categoryAxis.gridAlpha = 0.1;
	categoryAxis.minorGridAlpha = 0.1;
	categoryAxis.axisAlpha = 0;
	categoryAxis.minorGridEnabled = true;
	categoryAxis.inside = false;
	
	
	// CURSOR
	// chartCursor
	var chartCursor = new AmCharts.ChartCursor();
	chartCursor.categoryBalloonDateFormat = "YYYY/MM/DD";
	chartCursor.cursorColor = "#CC0000";
	chart.addChartCursor(chartCursor);
	chart.mouseWheelZoomEnabled = true;
	
	
	
	//clickGraphItem  rollOverGraphItem
	graph_cluster.addListener("rollOverGraphItem", function(event) {
		overData = event.item.dataContext;
		clusterData = overData.clusterData;
		$("#clusterdiv").empty();
		$("#clusterTitle").text(overData.date + " Cluster Detail");

		if(clusterData.length != null  && typeof clusterData.length != "undefined"){
			for(var i=0; i<clusterData.length; i++){
				clusterData[i].ti = parseFloat(clusterData[i].ti).toFixed(2);
				clusterData[i].pi = parseFloat(clusterData[i].pi).toFixed(2);
				clusterData[i].si = parseFloat(clusterData[i].si).toFixed(2);
				clusterData[i].tvi = parseFloat(clusterData[i].tvi).toFixed(2);
				clusterData[i].pvi = parseFloat(clusterData[i].pvi).toFixed(2);
				clusterData[i].ai = parseFloat(clusterData[i].ai).toFixed(2);
			}		
		}
		
		var subChart = new AmCharts.AmPieChart();
		subChart.valueField = "count";
		subChart.titleField = "Cluster Detail";
		subChart.dataProvider = clusterData;
		subChart.colors = ["#FF6600", "#FF9E01", "#FCD202", "#F8FF01", "#B0DE09"];
		
		subChart.labelText = "Cluster [[type]] : [[percents]]%";
		subChart.balloonText = "<span style='font-size:14px;'><b>Cluster [[type]]. Count : [[count]]</b>" +
				"<br>TI : [[ti]] PI : [[pi]] SI : [[si]]<br>TVI : [[tvi]] PVI : [[pvi]] AI : [[ai]]</span>";
		
		subChart.invalidateSize();
		subChart.startDuration = 0;
		subChart.write("clusterdiv");
		
	});
	
	
	
	chart.write("chartdiv");
}