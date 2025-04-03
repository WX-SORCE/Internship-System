/*
 * @Author: 赖宇凡  happystarrccat@gmail.com
 * @Date: 2025-04-02 16:03:46
 * @LastEditors: 赖宇凡  happystarrccat@gmail.com
 * @LastEditTime: 2025-04-02 16:05:25
 * @FilePath: \react-ui-master\src\pages\list\adit.js
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
import React, { useState } from "react";
import { Form, Input, Modal, Button, Row, Col, Spin, message, Space } from "antd";
import MyPagination from "@/components/pagination";
import MyTable from "@/components/table";
import { getApprovalList, approval } from "@/api";
import { Link } from "react-router-dom";


import "./index.less";

export default function SearchPage() {
    const [form] = Form.useForm();
    const [searchForm] = Form.useForm();
    const [pageData, setPageData] = useState({ page: 1 });
    const [tableData, setData] = useState([]);
    const [tableCol, setCol] = useState([]);
    const [load, setLoad] = useState(true);
    const [total, setTotal] = useState(0);
    const [showModal, setShow] = useState(false);

    const dataList = [
        {
            approval_id: '8SF7XA6R-7F8A-2JK4-12JS-12HBD7F8TJ9A',
            tradeId: "100000010",
            level: 2,
            approver_id: '1234321',
            decision: '通过',
            comment: '无审批意见',
            created_at: "2021-04-20 17:01:09"
        },
        {
            approval_id: '3F2504E0-4F89-11D3-9A0C-2UF7RHAK9C6E',
            tradeId: "100000011",
            level: 2,
            approver_id: '1234321',
            decision: '通过',
            comment: '无审批意见',
            created_at: "2021-04-20 17:01:09"
        },
        {
            approval_id: '2AS504E0-4F89-11D3-4ASC-0305E82C3301',
            tradeId: "100000012",
            level: 2,
            approver_id: '1234321',
            decision: '通过',
            comment: '无审批意见',
            created_at: "2021-04-20 17:01:09"
        },
        {
            approval_id: '3F224AE0-R52F-11D3-9A0C-0305E82C3301',
            tradeId: "100000013",
            level: 2,
            approver_id: '1234321',
            decision: '通过',
            comment: '无审批意见',
            created_at: "2021-04-20 17:01:09"
        },
    ];

    // 获取列表
    const getDataList = () => {
        const id =  JSON.parse(localStorage.getItem('USER_INFO')).userId
        getApprovalList(id).then((res) => {
            console.log(res)
            if (res.code === 0) {
                let list = res.data;
                if (res.data.tradeId) {
                    list = list.filter(item => item.tradeId === res.data.tradeId);
                }
                let total = list.length;
                let mapKey = [
                    { title: "审批记录ID", dataIndex: "approvalId", key: "approvalId", ellipsis: true },
                    { title: "交易流水号", dataIndex: "tradeId", key: "tradeId", ellipsis: true },
                    { title: "审批等级", dataIndex: "level", key: "level", ellipsis: true },
                    { title: "审批人编号", dataIndex: "approverId", key: "approverId", ellipsis: true },
                    { title: "通过/拒绝", dataIndex: "decision", key: "decision", ellipsis: true },
                    {
                        title: "审批意见",
                        key: "comment",
                        render: (text, record) => (
                            <Input
                                value={record.comment}
                                onChange={(e) => handleCommentChange(record, e.target.value)}
                            />
                        )
                    },
                    { title: "审批时间", dataIndex: "createdAt", key: "createdAt", ellipsis: true },
                    {
                        title: "操作",
                        key: "action",
                        render: (text, record) => (
                            <Space>
                                <Button type="primary" onClick={() => handleApprove(record)}>通过</Button>
                                <Button type="danger" onClick={() => handleReject(record)}>拒绝</Button>
                            </Space>
                        )
                    }
                ];
                mapKey = mapKey.map((i) => {
                    if (i.key === "comment") {
                        i.width = 200;
                    }
                    i.ellipsis = true;
                    return i;
                });
                setCol(mapKey);
                setTotal(total);
                setData(list.map((i) => ({ ...i, key: i.approvalId })));
                setLoad(false);
                return;
            }
        });
    };

    // 处理审批意见输入框的变化
    const handleCommentChange = (record, value) => {
        setData((prevData) =>
            prevData.map((item) => {
                if (item.approvalId === record.approvalId) {
                    return { ...item, comment: value };
                }
                return item;
            })
        );
    };

    // 处理通过按钮点击事件
    const handleApprove = (record) => {
        approval(record.approvalId, JSON.parse(localStorage.getItem('USER_INFO')).userId).then(res => {
            console.log( '++++++++++++++++' + record.approvalId)
            console.log( '++++++++++++++++' + JSON.parse(localStorage.getItem('USER_INFO')).userId)
            console.log(res)
        }).catch(err => {
            console.dir(err)
        })
        setData((prevData) =>
            prevData.map((item) =>
                item.tradeId === record.tradeId
                   ? { ...item, decision: '通过' }
                    : item
            )
        );
        message.success(`已通过 ${record.tradeId} 的审批`);
    };

    // 处理拒绝按钮点击事件
    const handleReject = (record) => {
        setData((prevData) =>
            prevData.map((item) =>
                item.tradeId === record.tradeId
                   ? { ...item, decision: '拒绝' }
                    : item
            )
        );
        message.error(`已拒绝 ${record.tradeId} 的审批`);
    };

    // 顶部搜索
    const search = (isSearch) => {
        setPageData({ page: 1 });
        let data = searchForm.getFieldsValue();
        let params = { ...data };
        if (!isSearch) {
            params = { ...params, ...pageData };
        }
        getDataList(params);
    };

    // 页码改版
    const pageChange = (pageData) => {
        let data = searchForm.getFieldsValue();
        getDataList({ ...pageData, ...data });
        setPageData(pageData);
    };

    return (
        <div className="search-container">
            <Spin spinning={load}>
                <div className="top-form">
                    <Form layout="inline" form={searchForm}>
                        <Form.Item name="tradeId" label="交易流水号">
                            <Input placeholder="输入交易流水号" />
                        </Form.Item>
                        <Button onClick={search} type="primary" className="submit-btn">
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
                    rowKey="tradeId"
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
    path: "/list/adit",
};
