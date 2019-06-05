import React, { Component } from 'react';
import '../../Styles/ErrorConnect.css';
class ErrorConnect extends Component {
    render() {
        return (
            <div>
                {/* Error Page */}
                <div className="error">
                    <div className="container-floud">
                        <div className="col-xs-12 ground-color text-center">
                            <div className="container-error-404">
                                <div className="clip"><div className="shadow"><span id="thirdDigit" className="digit thirdDigit" />5</div></div>
                                <div className="clip"><div className="shadow"><span id="secondDigit"  className="digit secondDigit" />0</div></div>
                                <div className="clip"><div className="shadow"><span id="firstDigit"  className="digit firstDigit" />0</div></div>
                                <div className="msg">OH!<span className="triangle" /></div>
                            </div>
                            <h2 className="h1">Sorry! Page not found</h2>
                        </div>
                    </div>
                </div>
                {/* Error Page */}

            </div>
        );
    }
}

export default ErrorConnect;