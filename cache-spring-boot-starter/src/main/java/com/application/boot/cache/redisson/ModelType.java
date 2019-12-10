package com.application.boot.cache.redisson;


/**
 * @author : 孤狼
 * @NAME: ModelType
 * @DESC: 模式的类型 simple,sentinel,masterslave,cluster,cloud
 **/
public enum ModelType {
	/**
	 *单机模式的调用
	 */
	SIMPLE("simple","单机模式的调用"),
	/**
	 *哨兵模式的调用
	 */
	SENTINEL("sentinel","哨兵模式的调用"),
	/**
	 *主从模式的调用
	 */
	MASTERSLAVE("masterslave","主从模式的调用"),
	/**
	 *集群模式的调用
	 */
	CLUSTER("cluster","集群模式的调用"),
	/**
	 * 云托管模式调用
	 */
	CLOUD("cloud","云托管模式调用");
	
	/**
	 * 模块
	 */
	private String model;
	/**
	 * 描述
	 */
	private String desc;
	
	ModelType(String model,String desc){
		this.model = model;
		this.desc = desc;
	}
	
	public String getModel() {
		return model;
	}
	
	public void setModel(String model) {
		this.model = model;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
