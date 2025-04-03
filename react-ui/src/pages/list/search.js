/*
 * @Author: 赖宇凡  happystarrccat@gmail.com
 * @Date: 2025-04-02 16:03:46
 * @LastEditors: 赖宇凡  happystarrccat@gmail.com
 * @LastEditTime: 2025-04-02 16:05:55
 * @FilePath: \react-ui-master\src\pages\list\search.js
 * @Description: 这是默认设置,请设置`customMade`, 打开koroFileHeader查看配置 进行设置: https://github.com/OBKoro1/koro1FileHeader/wiki/%E9%85%8D%E7%BD%AE
 */
import React, { useState } from "react";
import { Form, Input, Modal, Button, Row, Col, Spin, message, Space, Radio, DatePicker } from "antd";
import MyPagination from "@/components/pagination";
import MyTable from "@/components/table";
import { getMsg, addMsg, createRecommendList } from "@/api";
import { Link } from "react-router-dom";
import { useStateUserInfo } from "@/store/hooks";

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

    // 处理名称点击事件
    function createList (clientId) {
        const data = {"clientId": clientId,"userId": (JSON.parse(localStorage.getItem('USER_INFO'))).userId}
        console.log(data)
        createRecommendList(clientId,(JSON.parse(localStorage.getItem('USER_INFO'))).userId).then((res) => {
            console.log(res)
            if (res.code === 0) {
                // 显示成功提示框，持续2秒
                message.success('生成方案成功', 2);
            }
        })
    };
    const userInfo = useStateUserInfo()
    // 获取列表
    const getDataList = (data) => {
        const id = JSON.parse(localStorage.getItem('USER_INFO')).userId
        setLoad(true);
        getMsg(id).then((res) => {
            console.log(res)
            console.log(id)
            if (res.code === 0) {
                let list = res.data
                let total = res.data.length
                let mapKey = [
                    { title: "客户id", dataIndex: "clientId", key: "clientId", ellipsis: true },
                    {
                        title: "客户名称", dataIndex: "name", key: "name", ellipsis: true, render: (_, record) => {
                            return (
                                <Space size="middle">
                                    <Link to={`/detail`}>{record.name}</Link>
                                </Space>
                            );
                        },
                    },
                    { title: "邮箱", dataIndex: "email", key: "email", ellipsis: true },
                    { title: "手机号", dataIndex: "phoneNumber", key: "phoneNumber", ellipsis: true },
                    { title: "状态", dataIndex: "status", key: "status", ellipsis: true },
                    {
                        title: "操作",
                        key: "action",
                        render: (_,record) => (
                            <Space>
                                <Button type="primary" onClick={() => createList(record.clientId)}>生成方案</Button>
                            </Space>
                        )
                    }
                ];

                // 根据查询条件过滤数据
                if (data.name) {
                    list = list.filter(item => item.name === data.name);
                }

                setCol(mapKey);
                setTotal(list.length);
                setData(list.map((i) => ({ ...i, key: i.clientId })));
            }
            setLoad(false);
        }).catch((error) => {
            console.log(id)
            console.error("获取数据出错:", error);
            setLoad(false);
        });
    };

    // 添加列表
    const addList = () => {
        form.validateFields().then((values) => {
            const id = JSON.parse(localStorage.getItem('USER_INFO')).userId
            values.advisorId = userInfo.userId

            addMsg(id, values).then((res) => {
                console.log(res)
                if (res.code === 0) {
                    form.resetFields();
                    message.success(res.msg);
                    setShow(false);
                    search();
                }
            }).catch(err => {
                console.log(err)
            })
        });
    };

    // 顶部搜索
    const search = (isSearch = true) => {
        setPageData({ page: 1 });
        let data = searchForm.getFieldsValue();
        let params = { ...data, ...pageData };
        if (isSearch) {
            // 如果是搜索操作，把页码重置为第一页
            params.page = 1;
        }
        getDataList(params);
    };

    // 页码改版
    const pageChange = (pageData) => {
        let data = searchForm.getFieldsValue();
        getDataList({ ...pageData, ...data });
        setPageData(pageData);
    };
    const handleDownload = () => {
        const url = `http://localhost:8081/v1/client/export?advisorId=${userInfo.userId}&format=excel`;
        const a = document.createElement('a');
        a.href = url;
        a.download = 'exported_file.xlsx'; // 你可以自定义下载文件的名称，根据实际情况修改
        a.style.display = 'none';
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
    };
    const tableTop = (
        <Row justify="space-between" align="center" gutter={80}>
            <Col>
                <Button type="primary" onClick={() => setShow(true)}>
                    新增客户
                </Button>
            </Col>
            <Col>
                <Button type="primary" onClick={() => handleDownload(userInfo)}>
                    导出客户
                </Button>
            </Col>
        </Row>
    );
    const phoneRegex = /^1\d{10}$/;
    const assetRegex = /^([1 - 4]\d{2}|500)$/;

    const onFinish = (values) => {
        console.log('Form submitted:', values);
    };

    return (
        <div className="search-container">
            <Spin spinning={load}>
                <div className="top-form">
                    <Form layout="inline" form={searchForm}>
                        <Form.Item name="name" label="姓名">
                            <Input placeholder="输入姓名" />
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
                    title={() => tableTop}
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
            <Modal
                title="添加客户"
                visible={showModal}
                cancelText="取消"
                okText="添加"
                onOk={() => addList()}
                onCancel={() => setShow(false)}
                onFinish={onFinish}
                autoComplete="off"
            >
                <Form form={form}>
                    <Form.Item
                        label="姓名"
                        name="name"
                        rules={[
                            {
                                required: true,
                                message: "请输入姓名",
                            },
                        ]}
                    >
                        <Input />
                    </Form.Item>
                    <Form.Item
                        label="手机号"
                        name="phoneNumber"
                        rules={[
                            {
                                required: true,
                                message: "请输入手机号",
                            },
                            {
                                min: 11,
                                message: "手机号长度必须为 11 位",
                            },
                            {
                                pattern: phoneRegex,
                                message: "请输入正确格式的手机号",
                            },
                        ]}
                    >
                        <Input />
                    </Form.Item>
                    <Form.Item
                        label="密码"
                        name="password"
                        rules={[
                            {
                                required: true,
                                message: "请输入密码",
                            },
                            {
                                min: 6,
                                message: "密码长度至少为 6 位",
                            }
                        ]}
                    >
                        <Input.Password />
                    </Form.Item>
                    <Form.Item
                        label="国籍"
                        name="nationality"
                        rules={[
                            {
                                required: false,
                                message: "请输入国籍",
                            },
                            {
                                min: 2,
                                message: "国籍名长度必须为 2 位",
                            }
                        ]}
                    >
                        <Input />
                    </Form.Item>
                    <Form.Item
                        label="身份证号"
                        name="idNumber"
                        rules={[
                            {
                                required: false,
                                message: "请输入身份证号",
                            },
                            {
                                min: 18,
                                message: "身份证号长度必须为 18 位",
                            }
                        ]}
                    >
                        <Input />
                    </Form.Item>
                    <Form.Item
                        label="性别"
                        name="gender"
                        rules={[
                            {
                                required: false,
                                message: "请选择性别",
                            },
                        ]}
                    >
                        <Radio.Group>
                            <Radio value="m"> 男 </Radio>
                            <Radio value="f"> 女 </Radio>
                        </Radio.Group>
                    </Form.Item>
                    <Form.Item
                        label="邮箱账号"
                        name="email"
                        rules={[
                            {
                                required: false,
                                message: "请输入邮箱账号",
                            }
                        ]}
                    >
                        <Input />
                    </Form.Item>
                    <Form.Item
                        label="出生日期"
                        name="birthday"
                        rules={[
                            {
                                required: false,
                                message: "请填写出生日期",
                            },
                        ]}
                    >
                        <DatePicker />
                    </Form.Item>
                    <Form.Item
                        label="资产"
                        name="totalAssets"
                        rules={[
                            {
                                required: false,
                                message: "请输入资产",
                            },
                            // {
                            //     pattern: assetRegex,
                            //     message: "请输入格式为 纯数字 的资产",
                            // }
                        ]}
                    >
                        <Input />
                    </Form.Item>
                </Form>
            </Modal>
        </div>
    );
}

SearchPage.route = {
    path: "/list/search",
};