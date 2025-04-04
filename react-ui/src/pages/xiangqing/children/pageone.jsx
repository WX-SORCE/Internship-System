import React from "react";
import { List, Avatar } from "antd";
// import MyTable from "@/components/table";
// import { getMsg } from "@/api";
// const { TabPane } = Tabs;

const listDataArr = [
    {
        href: "https://ant.design",
        title: `ant design part 1`,
        avatar: "https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png",
        description:
            "Ant Design, a design language for background applications, is refined by Ant UED Team.",
        content:
            "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
    },
    {
        href: "https://ant.design",
        title: `ant design part 2`,
        avatar: "https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png",
        description:
            "Ant Design, a design language for background applications, is refined by Ant UED Team.",
        content:
            "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
    },
    {
        href: "https://ant.design",
        title: `ant design part 3`,
        avatar: "https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png",
        description:
            "Ant Design, a design language for background applications, is refined by Ant UED Team.",
        content:
            "We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.",
    },
];


function tabpanesone() {
    return (
        <List
            itemLayout="vertical"
            size="large"
            dataSource={listDataArr}
            renderItem={(item) => (
                <List.Item key={item.title}>
                    <List.Item.Meta
                        avatar={<Avatar src={item.avatar} />}
                        title={<a href={item.href}>{item.title}</a>}
                        description={item.description}
                    />
                    {item.content}
                </List.Item>
            )}
        />
   
    );

};


export default tabpanesone;