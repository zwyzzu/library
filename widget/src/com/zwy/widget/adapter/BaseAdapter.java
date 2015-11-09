/*************************************************************************************
 * Module Name: 具体模块见相应注释
 * File Name: FSBaseAdapter.java
 * Description: 
 * Author: 张维亚
 * All Rights Reserved
 * 所有版权保护
 * Copyright 2014, Funshion Online Technologies Ltd.
 * 版权 2014，北京风行在线技术有限公司
 * This is UNPUBLISHED PROPRIETARY SOURCE CODE of Funshion Online Technologies Ltd.;
 * the contents of this file may not be disclosed to third parties, copied or
 * duplicated in any form, in whole or in part, without the prior written
 * permission of Funshion Online Technologies Ltd.
 * 这是北京风行在线技术有限公司未公开的私有源代码。本文件及相关内容未经风行在线技术有
 * 限公司事先书面同意，不允许向任何第三方透露，泄密部分或全部; 也不允许任何形式的私自备份。
 ***************************************************************************************/
package com.zwy.widget.adapter;

import java.util.ArrayList;
import java.util.List;

import com.zwy.utils.Utils;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;

/**
 * Author: 张维亚
 * 创建时间：2014年6月19日 下午3:11:17
 * 修改时间：2014年6月19日 下午3:11:17
 * Description: 适配器基类
 **/
public class BaseAdapter<T> extends android.widget.BaseAdapter implements ListDataOperate<T>{

	private final int ADD_END = -1;
	private OnItemLoading<T> mItemLoading = null;
	protected List<T> mItems = new ArrayList<T>();

	public BaseAdapter(List<T> items, OnItemLoading<T> itemLoading) {
		super();
		this.setItems(items);
		this.mItemLoading = itemLoading;
	}

	@Override
	public void add(T t) {
		this.add(t, ADD_END);
	}

	@Override
	public void add(T t, int position) {
		this.addItem(t, position);
		this.notifyDataSetChanged();
	}

	@Override
	public void addAll(List<T> list) {
		this.addAll(list, ADD_END);
	}

	@Override
	public void addAll(List<T> list, int position) {
		this.addItems(list, position);
		this.notifyDataSetChanged();
	}

	@Override
	public void addCurrents(List<Current<T>> list) {
		if (Utils.isEmpty(list))
			return;

		for (Current<T> item : list)
			this.addItem(item.t, item.position);

		this.notifyDataSetChanged();
	}

	@Override
	public void remove(T t) {
		this.mItems.remove(t);
		this.notifyDataSetChanged();
	}

	@Override
	public void remove(int position) {
		this.removeItem(position);
		this.notifyDataSetChanged();
	}

	@Override
	public void removeAll(List<T> list) {
		if (Utils.isEmpty(list))
			return;
		this.mItems.removeAll(list);
		this.notifyDataSetChanged();
	}

	@Override
	public void replace(T t, int position) {
		this.replaceItem(t, position);
		this.notifyDataSetChanged();
	}

	@Override
	public void replaceCurrents(List<Current<T>> list) {
		if (Utils.isEmpty(list))
			return;

		for (Current<T> item : list)
			this.replaceItem(item.t, item.position);

		this.notifyDataSetChanged();
	}

	@Override
	public void reload(List<T> list) {
		this.setItems(list);
		this.notifyDataSetChanged();
	}

	@Override
	public void clear() {
		this.mItems.clear();
		this.notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return this.mItems.size();
	}

	@Override
	public T getItem(int position) {
		return this.mItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public final View getView(int position, View convertView, ViewGroup parent) {
		return this.mItemLoading.getView(convertView, getItem(position));
	}

	private void setItems(List<T> list) {
		if (this.mItems == list)
			return;
		this.mItems.clear();
		if (Utils.isEmpty(list)) {
			this.mItems.addAll(list);
		}
	}

	private boolean addItem(T t, int position) {
		if (addlast(position))
			return mItems.add(t);

		mItems.add(position, t);
		return true;
	}

	private boolean addItems(List<T> items, int position) {
		if (Utils.isEmpty(items))
			return false;
		if (addlast(position))
			return this.mItems.addAll(items);

		return this.mItems.addAll(position, items);
	}

	private boolean addlast(int position) {
		return position == ADD_END || position < 0 || position >= getCount();
	}

	private void replaceItem(T t, int position) {
		this.removeItem(position);
		this.addItem(t, position);
	}

	private T removeItem(int position) {
		return this.mItems.remove(position);
	}

	@Override
	public void unregisterDataSetObserver(DataSetObserver observer) {
		if (observer != null) {
			super.unregisterDataSetObserver(observer);
		}
	}

	public static interface OnItemLoading<T> {
		public View getView(View convertView, T item);
	}
}