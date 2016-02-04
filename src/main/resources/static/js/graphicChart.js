var chart;
var chartSub;
var charData;
var temp;



AmCharts.ready(function() {
			document.getElementById('backbtn').style.display = 'none';
			document.getElementById('analysisdiv').style.display = 'none';
			$("#legendDiv").css("display", "none");

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
			valueAxis.title = "Condition Index"
			valueAxis.tickLength = 0;
			valueAxis.axisAlpha = 0;
			valueAxis.minimum = 0;
			valueAxis.gridCount = 20;
			valueAxis.showFirstLabel = false;
			valueAxis.showLastLabel = false;
			valueAxis.zoomToValues(0, 10);
			valueAxis.position = "left";
			chart.addValueAxis(valueAxis);

			var valueAxis2 = new AmCharts.ValueAxis();
			valueAxis2.title = "Calorie Index";
			valueAxis2.lineThickness = 5;
			valueAxis2.tickLength = 0;
			valueAxis2.axisAlpha = 0;
			valueAxis2.showFirstLabel = false;
			valueAxis2.showLastLabel = false;
			valueAxis2.position = "right";
			chart.addValueAxis(valueAxis2);

			// GRAPHS
			// graph_cond
			var graph_cond = new AmCharts.AmGraph();
			graph_cond.type = "column";
			graph_cond.title = "Condition";
			graph_cond.lineColor = "#85C5E3";
			graph_cond.valueField = "conditionCalc";
			graph_cond.balloonText = "<b><span style='font-size:14px;'>[[conditionPoint]]</span></b>";
			graph_cond.valueAxis = valueAxis;
			graph_cond.fillAlphas = 0.95;
			graph_cond.cornerRadiusTop = 5;
			chart.addGraph(graph_cond);

			// chartSub
			
			chartSub = new AmCharts.AmSerialChart();
			chartSub.dataProvider = charData;
			chartSub.dataDateFormat = "HH:NN:SS";
			chartSub.categoryField = "date";
			chartSub.creditsPosition = "top-left";
			
			
			var legendSub = new AmCharts.AmLegend();
			legendSub.useGraphSettings = false;

			
			chartSub.addLegend(legendSub);

			// AXES
			// categoryAxis
			var categoryAxis = chart.categoryAxis;
			categoryAxis.parseDates = true;
			categoryAxis.minPeriod = "DD";
			categoryAxis.gridAlpha = 0.1;
			categoryAxis.minorGridAlpha = 0.1;
			categoryAxis.axisAlpha = 0;
			categoryAxis.minorGridEnabled = true;
			categoryAxis.inside = false;

			var categoryAxis2 = chartSub.categoryAxis;
			categoryAxis2.parseDates = true;
			categoryAxis2.minPeriod = "mm";
			categoryAxis2.gridAlpha = 0.1;
			categoryAxis2.minorGridAlpha = 0.1;
			categoryAxis2.axisAlpha = 0;
			categoryAxis2.minorGridEnabled = true;
			categoryAxis2.inside = false;

			
			var valueAxis3 = new AmCharts.ValueAxis();
			valueAxis3.tickLength = 0;
			valueAxis3.axisAlpha = 0;
			valueAxis3.showFirstLabel = false;
			valueAxis3.showLastLabel = false;
			chartSub.addValueAxis(valueAxis3);

			
			
			// graph_cal
			var graph_cal = new AmCharts.AmGraph();
			graph_cal.type = "line";
			graph_cal.title = "Calorie";
			graph_cal.lineColor = "#00CC00";
			graph_cal.valueField = "caloriePoint";
			graph_cal.fillAlphas = 0;
			graph_cal.bullet = "round";
			graph_cal.bulletSize = "7";
			graph_cal.balloonText = "<b><span style='font-size:14px;'>[[caloriePoint]]</span></b>";
			graph_cal.valueAxis = valueAxis2;
			chart.addGraph(graph_cal);

			// graph_temp
			var graph_temp = new AmCharts.AmGraph();
			graph_temp.type = "line";
			graph_temp.lineThickness = 2.5;
			graph_temp.title = "Temperature";
			graph_temp.lineColor = "#00CC00";
			graph_temp.valueField = "temperature";
			graph_temp.fillAlphas = 0;
			graph_temp.balloonText = "<b><span style='font-size:14px;'>[[temperature]]</span></b>";
			chartSub.addGraph(graph_temp);

			// graph_hr
			var graph_hr = new AmCharts.AmGraph();
			graph_hr.type = "line";
			graph_hr.lineThickness = 2.5;
			graph_hr.title = "Heart Rate";
			graph_hr.lineColor = "#FF8600";
			graph_hr.valueField = "heart_rate";
			graph_hr.fillAlphas = 0;
			graph_hr.balloonText = "<b><span style='font-size:14px;'>[[heart_rate]]</span></b>";
			chartSub.addGraph(graph_hr);

			// graph_step
			var graph_step = new AmCharts.AmGraph();
			graph_step.type = "column";
			graph_step.title = "Step";
			graph_step.lineColor = "#770055";
			graph_step.valueField = "step";
			graph_step.fillAlphas = 0.7;
			graph_step.balloonText = "<b><span style='font-size:14px;'>[[step]]</span></b>";
			graph_step.textAlign = "left";
			chartSub.addGraph(graph_step);

			// CURSOR
			// chartCursor
			var chartCursor = new AmCharts.ChartCursor();
			chartCursor.categoryBalloonDateFormat = "YYYY/MM/DD";
			chartCursor.cursorColor = "#CC0000";
			chart.addChartCursor(chartCursor);
			chart.mouseWheelZoomEnabled = true;
			
			
			
			chart.addListener("clickGraphItem", function(event) {
								// 클릭한 데이터
								clickData = event.item.dataContext;
								var sensingData = clickData.sensingDataList;
				
				
								// 데이터가 비었으면
								if(typeof sensingData[0] == 'undefined' 
									|| sensingData.length == 0){
									
									alert("Empty data");
									
									return;
								
								
								
								}else if (typeof sensingData[0] != 'undefined') { // 센싱데이터가 있을 때만
									
									var button = document.getElementById('backbtn');
									document.getElementById('backbtn').style.display = 'block';
									document.getElementById('analysisdiv').style.display = 'block';
									$("#legendDiv").css("display", "block");

									button.onclick = function() {
										document.getElementById('backbtn').style.display = 'none';
										document.getElementById('analysisdiv').style.display = 'none';
										$("#legendDiv").css("display", "none");
										resetChart();
									}

									
									// 최고-최저-평균 계산
									var sensingDataLength = sensingData.length;
									var HighTemp = sensingData[0].temperature, LowTemp = sensingData[0].temperature, HighHeart = sensingData[0].heart_rate, LowHeart = sensingData[0].heart_rate, HighStep = sensingData[0].step, LowStep = sensingData[0].step, TotalTemp = 0, TotalHeart = 0, TotalStep = 0;
									var stepCount = 0;
									
									for (var i = 1; i < sensingDataLength; i++) {
										// temperature
										var temp = parseFloat(sensingData[i].temperature);
										if (temp < LowTemp)
											LowTemp = temp;
										if (temp > HighTemp)
											HighTemp = temp;
										TotalTemp += temp;

										// Heart
										var heart = parseInt(sensingData[i].heart_rate);
										if (heart < LowHeart)
											LowHeart = heart;
										if (heart > HighHeart)
											HighHeart = heart;
										TotalHeart += heart;

										// Step
										var step = parseInt(sensingData[i].step);
										if(step > 0){
											if (step < LowStep)
												LowStep = step;
											if (step > HighStep)
												HighStep = step;
											TotalStep += step;
											stepCount++;
										}
									}

									// toFixed로 소수점 제한
									$("#tempMin").text(LowTemp);
									$("#tempMax").text(HighTemp);
									$("#tempAvg").text(
											(TotalTemp / stepCount)
													.toFixed(2));

									$("#heartMin").text(LowHeart);
									$("#heartMax").text(HighHeart);
									$("#heartAvg").text(
											(TotalHeart / sensingDataLength)
													.toFixed(2));

									$("#stepMin").text(LowStep);
									$("#stepMax").text(HighStep);
									$("#stepAvg").text(
											(TotalStep / sensingDataLength)
													.toFixed(2));

								}
								// 계산 끝

								// CURSOR
								// chartCursorSub
								var chartCursorSub = new AmCharts.ChartCursor();
								chartCursorSub.categoryBalloonDateFormat = "HH:NN:SS";
								chartCursorSub.cursorColor = "#CC0000";
								chartSub.addChartCursor(chartCursorSub);
								chartSub.mouseWheelZoomEnabled = true;
								clickData = event.item.dataContext;
								chartSub.dataProvider = event.item.dataContext.sensingData;
								var contents = [ "ConditionDetail_",
										"Temperature_", "TemperatureChange_",
										"TemperatureRhythm_", "HeartRate_",
										"HeartRateChange_", "HeartRateRhythm_",
										"Synchronization_", "Activity_" ];

								var points = [
										clickData.conditionPoint,
										clickData.conditionData.tempPoint,
										clickData.conditionData.tempChangeDeductPoint,
										clickData.conditionData.tempRhythmPoint,
										clickData.conditionData.hrPoint,
										clickData.conditionData.hrChangeDeductPoint,
										clickData.conditionData.hrRhythmPoint,
										clickData.conditionData.synchroDeductPoint,
										clickData.conditionData.activityPoint ];


								for (var i = 0; i < 9; i = i + 1) {
									$.ajax({
										url : '/handy/DataAnalysis',
										type : 'GET',
										async : false,
										data : {
											'content' : contents[i],
											'point' : points[i]
										},
										success : function(data) {
											// 줄바꾸기 필요 시 수정
											$('#analysis' + i).text(data);
										}
									});
								}

								chartSub.mouseWheelZoomEnabled = true;

								chartSub.validateData();
								//chartSub.animateAgain();
								chartSub.invalidateSize(); // 차트크기 일정하게
								chartSub.write("chartdiv");
							});
			chart.write("chartdiv");
		});

function resetChart() {
	chart.dataProvider = data;
	chart.validateData();
//	chart.animateAgain();
	chart.invalidateSize(); // 차트크기 일정하게
	chart.allLabels = [];
	chart.write("chartdiv");
}