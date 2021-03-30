/*
 * Copyright (C) 2018 matheus
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package workers;

import ferramentaic.RUser;
import java.util.ResourceBundle;

/**
 *
 * @author matheus
 */
public class ApplyChanges extends AgreeCalcWorker {

    private String diretorio;
    private boolean resp;
    
    public ApplyChanges(ResourceBundle bundle, String diretorio) {
        super(bundle);
        this.diretorio = diretorio;
        resp=false;
    }

    @Override
    public Object getResponse() {
        return resp;
    }

    @Override
    protected Object doInBackground() throws Exception {
        this.setStatusMessage(this.bundle.getString("Reprocessing"));
        resp = RUser.getInstance().setDados(diretorio);
        return resp;
    }
    
}
