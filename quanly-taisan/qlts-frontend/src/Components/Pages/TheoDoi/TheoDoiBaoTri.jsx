import React, { Component } from "react";
import { Timeline, Icon, Input } from "antd";
import {getbyMaTBBaoTri } from "../../../Services/apitan";

class TheoDoiBaoTri extends Component {

    constructor(props){
        super(props);
        this.state = {
            theodoi : [],
        }
    }

  onSearch = async (value) => {
    console.log(value);
    let data = await getbyMaTBBaoTri(value);
    this.setState({
        theodoi : data
    });
  };
  render() {
      var {theodoi} = this.state;
      console.log(theodoi);
    return (
      <div>
        <Search
          style={{ width: 300 }}
          placeholder="Nhập Mã Thiết Bị"
          enterButton="Search"
          size="16px"
          onSearch={this.onSearch}
        />
        <Timeline
          mode="alternate"
          style={{ marginTop: 80, marginLeft: 400, width: 300 }}
        >
          <Timeline.Item title="maThietBi">
            Create a services site 2015-09-01
          </Timeline.Item>
          <Timeline.Item color="green">
            Solve initial network problems 2015-09-01
          </Timeline.Item>
          <Timeline.Item
            dot={<Icon type="clock-circle-o" style={{ fontSize: "16px" }} />}
          />
          <Timeline.Item color="red">
            Network problems being solved 2015-09-01
          </Timeline.Item>
          <Timeline.Item>Create a services site 2015-09-01</Timeline.Item>
          <Timeline.Item
            dot={<Icon type="clock-circle-o" style={{ fontSize: "16px" }} />}
          >
            Technical testing 2015-09-01
          </Timeline.Item>
        </Timeline>
        ,
      </div>
    );
  }
}
const Search = Input.Search;
export default TheoDoiBaoTri;
