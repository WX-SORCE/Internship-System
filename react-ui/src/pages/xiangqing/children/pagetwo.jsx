import React, { useState, useEffect } from "react";
import { message } from "antd";
import MyPagination from "@/components/pagination";
import MyTable from "@/components/table";
import { getMsg } from "@/api";




function Tabpanestwo() {
    const [total, setTotal] = useState(0);
    const [tableData, setData] = useState([]);
    const [pageData, setPageData] = useState({ page: 1 });
    const [tableCol, setCol] = useState([]);

    const pageChange = (pageData) => {
        getDataList({ ...pageData });
        setPageData(pageData);
    };

    const getDataList = (data) => {
        getMsg(data).then((res) => {
            if (res && res.status === 0) {
                const { list, total } = res.data;
                const mapKey = [
                    { title: "消息id", dataIndex: "m_id", key: "m_id", ellipsis: true },
                    {
                        title: "客户名称",
                        dataIndex: "name",
                        key: "name",
                        ellipsis: true,
                    },
                    {
                        title: "消息描述词",
                        dataIndex: "description",
                        key: "description",
                        ellipsis: true,
                    },
                    { title: "创建人", dataIndex: "creator", key: "creator", ellipsis: true },
                    {
                        title: "创建时间",
                        dataIndex: "add_time",
                        key: "add_time",
                        ellipsis: true,
                    },
                ];
                setCol(mapKey);
                setTotal(total);
                setData(list.map((i) => ({ ...i, key: i.m_id })));
            } else {
                message.error("获取数据失败");
            }
        });
    };

    useEffect(() => {
        getDataList({ page: 1 });
    }, []);

    return (
        <div>
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
        </div>
    );
};


export default Tabpanestwo;