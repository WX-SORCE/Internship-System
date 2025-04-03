import React from 'react';
import './ModuleItem.less';

const ModuleItem = ({ title = '', loading = false, children }) => {
    return (
        <div className="ModuleItem">
            {title && <h2>{title}</h2>}
            {children}
            {loading && <div className="circle-3"></div>}
        </div>
    );
};

export default ModuleItem;