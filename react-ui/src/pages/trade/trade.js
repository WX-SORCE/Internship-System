/*
 * @Author: 赖宇凡  happystarrccat@gmail.com
 * @Date: 2025-04-02 16:03:46
 * @LastEditors: 赖宇凡  happystarrccat@gmail.com
 * @LastEditTime: 2025-04-02 16:06:14
 * @FilePath: \react-ui-master\src\pages\trade\trade.js
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
import React, { useState, useEffect } from "react";
import { Form, Input, Modal, Button, Row, Col, Spin, message } from "antd";
import MyPagination from "@/components/pagination";
import MyTable from "@/components/table";
import { getTradeMsg, addTradeMsg } from "@/api";
import { useStateUserInfo } from "@/store/hooks";

// import { useNavigate } from 'react-router-dom';

import "./index.less";
// import { Router } from "react-router-dom/cjs/react-router-dom.min";

export default function SearchPage() {
    const [form] = Form.useForm();
    const [searchForm] = Form.useForm();
    const [pageData, setPageData] = useState({ page: 1 });
    const [tableData, setData] = useState([]);
    const [tableCol, setCol] = useState([]);
    const [load, setLoad] = useState(true);
    const [total, setTotal] = useState(0);
    const [showModal, setShow] = useState(false);

    const trademsgList = [
        {
            trade_id: 'T202503251812',
            client_id: "110010010",
            product_code: "202503251812",
            type: '申购',
            amount: '120',
            status: '待批审',
            created_at: "2021-04-20 17:01:09",
            updated_at: "2021-04-20 17:01:09"
        },
        {
            trade_id: 'T202503251812',
            client_id: "100000010",
            product_code: "202503251812",
            type: '申购',
            amount: '120',
            status: '待批审',
            created_at: "2021-04-20 17:01:09",
            updated_at: "2021-04-20 17:01:09"
        },
        {
            trade_id: 'T202503251812',
            client_id: "100000010",
            product_code: "202503251812",
            type: '申购',
            amount: '120',
            status: '待批审',
            created_at: "2021-04-20 17:01:09",
            updated_at: "2021-04-20 17:01:09"
        },
        {
            trade_id: 'T202503251812',
            client_id: "100000010",
            product_code: "202503251812",
            type: '申购',
            amount: '120',
            status: '待批审',
            created_at: "2021-04-20 17:01:09",
            updated_at: "2021-04-20 17:01:09"
        },
    ];
    // 页面进入调取接口
    useEffect(() => {
        search()
    }, []);
    const userInfo = useStateUserInfo()

    // 获取列表
    const getDataList = (data) => {
        setLoad(true);
        getTradeMsg(data)
            .then((res) => {
                console.log(res)
                // if (res.status === 0) {
                    let list = res.data;
                    // if (data.client_id) {
                    //     list = list.filter(item => item.client_id === data.client_id);
                    // }
                    const total = list.length;
                    const mapKey = [
                        { title: "交易id", dataIndex: "tradeId", key: "tradeId", ellipsis: true },
                        { title: "客户id", dataIndex: "clientId", key: "clientId", ellipsis: true },
                        { title: "产品编号", dataIndex: "productCode", key: "productCode", ellipsis: true },
                        { title: "类型", dataIndex: "type", key: "type", ellipsis: true },
                        { title: "金额或者数量", dataIndex: "amount", key: "amount", ellipsis: true },
                        { title: "状态", dataIndex: "status", key: "status", ellipsis: true },
                        { title: "创建时间", dataIndex: "createTime", key: "createTime", ellipsis: true },
                        { title: "更新时间", dataIndex: "updateTime", key: "updateTime", ellipsis: true }
                    ];
                    setCol(mapKey);
                    setTotal(total);
                    setData(list);
                    // message.success(`chau: ${error.message}`);
                // } else {
                //     message.error('获取数据失败');
                // }
            })
            .catch((error) => {
                message.error(`请求出错: ${error.message}`);
            })
            .finally(() => {
                setLoad(false);
            });
    };

    // 顶部搜索
    const search = (isSearch) => {
        setPageData({ page: 1 });
        const data = searchForm.getFieldsValue();
        const params = { ...data};
        params.clientId = userInfo.clientId;
        console.log("交易传递参数",params)
        if (!isSearch) {
            params.page = pageData.page;
        }
        getDataList(params);
    };

    // 页码改版
    const pageChange = (newPageData) => {
        const data = searchForm.getFieldsValue();
        newPageData.clientId = userInfo.clientId;

        getDataList({ ...newPageData, ...data });
        setPageData(newPageData);
    };

    return (
        <div className="search-container">
            <Spin spinning={load}>
                <div className="top-form">
                    <Form layout="inline" form={searchForm}>
                        <Form.Item name="tradeId" label="交易 id">
                            <Input placeholder="输入交易 id" />
                        </Form.Item>
                        <Button onClick={() => search(true)} type="primary" className="submit-btn">
                            查询
                        </Button>
                        <Button
                            onClick={() => {
                                searchForm.resetFields();
                                search();
                            }}
                        >
                            重置
                        </Button>
                    </Form>
                </div>
                <MyTable
                    dataSource={tableData}
                    columns={tableCol}
                    pagination={false}
                    saveKey="listForm"
                    rowKey="m_id"
                />
                <MyPagination
                    page={pageData.page}
                    immediately={getDataList}
                    change={pageChange}
                    total={total}
                />
            </Spin>
        </div>
    );
}

SearchPage.route = {
    path: "/trade",
};
