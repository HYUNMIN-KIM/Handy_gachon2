//TODO
function chartdiv(id, cData) {
	var barColor = '#448DD6';
	function segColor(c) {
		return {
			A : "#807dba",
			B : "#DF7C7F",
			C : "#41ab5d",
			D : "#FF7F0E",
			E : "#BDBDBD"
		}[c];
	}

	cData.forEach(function(d) {
		d.date = d.date;
		d.type = d.type;
		d.ti = d.ti;
	});

	function typestoGram(cD) {
		var hG = {}, hGDim = {
			t : 5,
			r : 0,
			b : 30,
			l : 30
		};
		hGDim.w = 450 - hGDim.l - hGDim.r, hGDim.h = 300 - hGDim.t - hGDim.b;

		// svg 만들기
		var hGsvg = d3.select(id).append("svg").attr("width",
				hGDim.w + hGDim.l + hGDim.r).attr("height",
				hGDim.h + hGDim.t + hGDim.b).append("g").attr("transform",
				"translate(" + hGDim.l + "," + hGDim.t + ")");

		var x = d3.scale.ordinal().rangeRoundBands([ 0, hGDim.w ], 0.1).domain(
				cD.map(function(d) {
					return d[0];
				}));

		var y = d3.scale.linear().range([ hGDim.h, 0 ]).domain(
				[ 0, d3.max(cD, function(d) {
					return d[1];
				}) ]);

		hGsvg.append("g").attr("class", "x axis").attr("transform",
				"translate(0," + hGDim.h + ")").call(
				d3.svg.axis().scale(x).orient("bottom"));

		hGsvg.append("g").attr("class", "y axis").call(
				d3.svg.axis().scale(y).orient("left"));

		// mouseover할 때, tip 구현
		var tip = d3.tip().attr('class', 'd3-tip').offset([ -10, 0 ]).html(
				function(d) {
					
					if (d[1] == '5')
						d[1] = 'A';
					else if (d[1] == '4')
						d[1] = 'B';
					else if (d[1] == '3')
						d[1] = 'C';
					else if (d[1] == '2')
						d[1] = 'D';
					else if (d[1] == '1')
						d[1] = 'E';
					return "<strong>RANK : </strong><span style='color:red'>"
							+ d[1] + "</span>";
				});
		hGsvg.call(tip);

		// bar chart 만들기
		var bars = hGsvg.selectAll(".bar").data(cD).enter().append("g").attr(
				"class", "bar");

		// rectangle 만들기
		bars.append("rect").attr("x", function(d) {
			return x(d[0]);
		}).attr("y", function(d) {
			return y(d[1]);
		}).attr("width", x.rangeBand()).attr("height", function(d) {
			return hGDim.h - y(d[1]);
		}).attr('fill', barColor).on("mouseover", mouseover).on("mouseout",
				mouseout);

		// tip 실행
		bars.on('mouseover', tip.show).on('mouseout', tip.typede);

		// mouseover
		function mouseover(d) {
			var st = cData.filter(function(s) {
				return s.date == d[0];
			})[0], nD = d3.keys(st.cluster).map(function(s) {
				return {
					type : s,
					cluster : st.cluster[s]
				};
			});

			pC.update(nD);
			leg.update(nD);
		}

		// mouseout
		function mouseout(d) {
			pC.update(tF);
			leg.update(tF);
		}

		hG.update = function(nD, color) {
			y.domain([ 0, d3.max(nD, function(d) {
				return d[1];
			}) ]);

			var bars = hGsvg.selectAll(".bar").data(nD);

			bars.select("rect").transition().duration(500).attr("y",
					function(d) {
						return y(d[1]);
					}).attr("height", function(d) {
				return hGDim.h - y(d[1]);
			}).attr("fill", color);

			bars.select("text").transition().duration(500).text(function(d) {
				return d3.format(",")(d[1])
			}).attr("y", function(d) {
				return y(d[1]) - 5;
			});
		}
		return hG;
	}

	// pieChart.
	function pieChart(pD) {
		var pC = {}, pieDim = {
			w : 300,
			h : 250
		};
		pieDim.r = Math.min(pieDim.w, pieDim.h) / 2;

		// svg 만들기
		var piesvg = d3.select(id).append("svg").attr("width", pieDim.w).attr(
				"height", pieDim.h).append("g").attr("transform",
				"translate(" + pieDim.w / 2 + "," + pieDim.h / 2 + ")");

		var arc = d3.svg.arc().outerRadius(pieDim.r - 10).innerRadius(0);

		var pie = d3.layout.pie().sort(null).value(function(d) {
			return d.cluster;
		});

		piesvg.selectAll("path").data(pie(pD)).enter().append("path").attr("d",
				arc).each(function(d) {
			ttypes._current = d;
		}).style("fill", function(d) {
			return segColor(d.data.type);
		}).on("mouseover", mouseover).on("mouseout", mouseout);

		pC.update = function(nD) {
			piesvg.selectAll("path").data(pie(nD)).transition().duration(500)
					.attrTween("d", arcTween);
		}

		// mouseover
		function mouseover(d) {
			// hG.update(cData.map(function(v) {
			// return [ v.date, v.cluster[d.data.type] ];
			// }), segColor(d.data.type));
		}

		// mouseout
		function mouseout(d) {
			// hG.update(cData.map(function(v) {
			// return [ v.date, v.type, v.pi ];
			// }), barColor);
		}

		// intermediate
		function arcTween(a) {
			var i = d3.interpolate(ttypes._current, a);
			ttypes._current = i(0);
			return function(t) {
				return arc(i(t));
			};
		}
		return pC;
	}

	// legend.
	function legend(lD) {
		console.log(cData[0]);
		console.log(lD[1]);

		var leg = {};
		var legend = d3.select(id).append("table").attr('class', 'legend');
		var tr = legend.append("tbody").selectAll("tr").data(lD).enter()
				.append("tr");

		tr.append("td").append("svg").attr("width", '16').attr("height", '16')
				.append("rect").attr("width", '16').attr("height", '16').attr(
						"fill", function(d) {
							return segColor(d.type);
						});

		tr.append("td").text(function(d) {
			return d.type;
		});

		// 사람 총 합
		tr.append("td").attr("class", 'lgNumOfPeople').text(function(d) {
			return d3.format(",")(d.cluster);
		});

		// % 계산
		tr.append("td").attr("class", 'lgPercent').text(function(d) {
			return getLegend(d, lD);
		});

		// Index TI
		tr.append("td").attr("class", 'lgIndexTI').text(function(d) {
			return "TI: " + cData[0].ti;
		});

		// Index PI
		tr.append("td").attr("class", 'lgIndexPI').text(function(d) {
			return "PI: " + cData[0].pi;
		});

		// Index SI
		tr.append("td").attr("class", 'lgIndexSI').text(function(d) {
			return "SI: " + cData[0].si;
		});

		// Index TVI
		tr.append("td").attr("class", 'lgIndexTVI').text(function(d) {
			return "TVI: " + cData[0].tvi;
		});

		// Index PVI
		tr.append("td").attr("class", 'lgIndexPVI').text(function(d) {
			return "PVI: " + cData[0].pvi;
		});

		// Index AI
		tr.append("td").attr("class", 'lgIndexAI').text(function(d) {
			return "AI: " + cData[0].ai;
		});

		leg.update = function(nD) {
			var l = legend.select("tbody").selectAll("tr").data(nD);

			l.select(".lgNumOfPeople").text(function(d) {
				return d3.format(",")(d.cluster);
			});

			l.select(".lgPercent").text(function(d) {
				return getLegend(d, nD);
			});
		}

		function getLegend(d, aD) {
			return d3.format("%")(d.cluster / d3.sum(aD.map(function(v) {
				return v.cluster;
			})));
		}

		return leg;
	}

	var tF = [ 'A', 'B', 'C', 'D', 'E' ].map(function(d) {
		return {
			type : d,
			cluster : d3.sum(cData.map(function(t) {
				return t.cluster[d];
			}))
		};
	});

	// calculate total clusteruency by date for all segment.
	var sF = cData.map(function(d) {
		return [ d.date, d.type, d.ti ];
	});

	var hG = typestoGram(sF), // create the typestogram.
	pC = pieChart(tF), // create the pie-chart.
	leg = legend(tF); // create the legend.
}

var clusterData = [ {
	date : '2016/02/10',
	type : 5,
	ti : 3,
	pi : 3,
	si : 3,
	tvi : 3,
	pvi : 3,
	ai : 3,
	cluster : {
		A : 2786,
		B : 1319,
		C : 249,
		D : 540,
		E : 784
	}
}, {
	date : '2016/02/11',
	type : 3,
	ti : 3,
	pi : 3,
	si : 3,
	tvi : 3,
	pvi : 3,
	ai : 3,
	cluster : {
		A : 901,
		B : 412,
		C : 674,
		D : 232,
		E : 454
	}
}, {
	date : '2016/02/12',
	type : 1,
	ti : 3,
	pi : 3,
	si : 3,
	tvi : 3,
	pvi : 3,
	ai : 3,
	cluster : {
		A : 932,
		B : 2149,
		C : 418,
		D : 201,
		E : 653
	}
}, {
	date : '2016/02/13',
	type : 2,
	ti : 3,
	pi : 3,
	si : 3,
	tvi : 3,
	pvi : 3,
	ai : 3,
	cluster : {
		A : 832,
		B : 1152,
		C : 1862,
		D : 454,
		E : 511
	}
}, {
	date : '2016/02/14',
	type : 3,
	ti : 3,
	pi : 3,
	si : 3,
	tvi : 3,
	pvi : 3,
	ai : 3,
	cluster : {
		A : 4481,
		B : 3304,
		C : 948,
		D : 142,
		E : 453
	}
}, {
	date : '2016/02/15',
	type : 3,
	ti : 3,
	pi : 3,
	si : 3,
	tvi : 3,
	pvi : 3,
	ai : 3,
	cluster : {
		A : 1619,
		B : 167,
		C : 1063,
		D : 554,
		E : 232
	}
}, {
	date : '2016/02/16',
	type : 4,
	ti : 3,
	pi : 3,
	si : 3,
	tvi : 3,
	pvi : 3,
	ai : 3,
	cluster : {
		A : 1819,
		B : 247,
		C : 1203,
		D : 674,
		E : 443
	}
} ];

chartdiv('#chartdiv', clusterData);