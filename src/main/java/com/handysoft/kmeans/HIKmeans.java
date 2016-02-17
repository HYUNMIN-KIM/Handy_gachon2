package com.handysoft.kmeans;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.handysoft.model.HIClusterData;

import net.sf.javaml.clustering.Clusterer;
import net.sf.javaml.clustering.KMeans;
import net.sf.javaml.core.Dataset;
import net.sf.javaml.tools.DatasetTools;
import net.sf.javaml.tools.data.FileHandler;

public class HIKmeans {
	private static final int CLUSTER_SIZE = 5;

	
	// 이 메소드에 전체 사용자들의 HI들이 담긴 dataset과 해당 연월일을 넣으면
	// HI로 계산한 cluster를 리턴함
	public static List<HIClusterData> getClusters(Dataset data, int year, int month, int day) throws IOException {
		
		List<HIClusterData> hiClusterData = new ArrayList<>();
		
		//set
		Clusterer clusterer = new KMeans(CLUSTER_SIZE);

		// clustering
		Dataset[] clusters = clusterer.cluster(data);
		
		for(Dataset dataset : clusters){
			
			
			
			hiClusterData
			.add(
					new HIClusterData(year, month, day, 
							DatasetTools.average(dataset).get(0), 
							DatasetTools.average(dataset).get(1), 
							DatasetTools.average(dataset).get(2), 
							DatasetTools.average(dataset).get(3), 
							DatasetTools.average(dataset).get(4), 
							DatasetTools.average(dataset).get(5),
							dataset.size()
							)
					);
		}
		
		
		// Cluster sort
		hiClusterData.sort(new Comparator<HIClusterData>() {

			@Override
			public int compare(HIClusterData o1, HIClusterData o2) {
				//XXX hicluster sorting algorithm
				if(o1.getHi() > o2.getHi())
					return 1;
				else if(o1.getHi() < o2.getHi())
					return -1;
				else
					return 0;
			}
		});
		
		
		
		// Cluster typing
		char rank = 'A';
		for(HIClusterData h : hiClusterData)
			h.setType(rank++);
		
		
		
		
		return hiClusterData;
	}

}
