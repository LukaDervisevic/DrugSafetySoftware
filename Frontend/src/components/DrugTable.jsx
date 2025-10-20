// import { useRef } from "react";
import {
  AutoSizer,
  List,
  CellMeasurer,
  CellMeasurerCache,
} from "react-virtualized";
import { useEffect } from "react";

function Table({ lekovi, onRowClick }) {
  // const [selectedIndex, setSelectedIndex] = useState(null);

  const cachedMeasure = new CellMeasurerCache({
    defaultHeight: 50, // visina reda pre nego što se izmeri
    fixedWidth: true, // širina je fiksna
  });

  useEffect(() => {
    cachedMeasure.clearAll();
  }, [lekovi]);

  const rowRenderer = ({ index, key, style, parent }) => {
    const lek = lekovi[index];

    return (
      <CellMeasurer
        key={key}
        cache={cachedMeasure}
        columnIndex={0}
        rowIndex={index}
        parent={parent}
      >
        {() => (
          <div
            style={{
              ...style,
              fontSize: "12px",
              display: "flex",
              flexDirection: "row",
              borderBottom: "1px solid #e5e7eb",
              padding: "10px 16px",
              lineHeight: "1.4",
              backgroundColor: index % 2 === 0 ? "#fff" : "#f9fafb",
            }}
            onClick={() => onRowClick(lek)}
          >
            <div style={{ width: "10%", fontWeight: 600 }}>{lek.nazivLeka}</div>
            <div style={{ width: "20%", color: "#4b5563" }}>{lek.inn}</div>
            <div style={{ width: "40%" }}>{lek.oblikIDozaLeka}</div>
            <div style={{ width: "10%", color: "#4b5563" }}>
              {lek.nosilacDozvole}
            </div>
            <div style={{ width: "20%" }}>{lek.proizvodjac}</div>
          </div>
        )}
      </CellMeasurer>
    );
  };

  return (
    <div className="bordered h-[400px]">
      {/* Header */}
      <div className="flex flex-row bg-gray-100 font-semibold px-4 py-2 border-b sticky top-0 z-10 table-header">
        <div className="w-[10%] pl-[10px]">Naziv leka</div>
        <div className="w-[20%] pl-[10px]">Sastav</div>
        <div className="w-[40%]">Oblik i doza</div>
        <div className="w-[10%]">Nosilac Dozvole</div>
        <div className="w-[20%]">Proizvodjac</div>
      </div>

      <AutoSizer>
        {({ height, width }) => (
          <List
            height={height - 40} // minus visina headera
            width={width}
            rowCount={lekovi.length}
            rowHeight={cachedMeasure.rowHeight} // koristi funkciju iz cache-a
            deferredMeasurementCache={cachedMeasure} // važno za dinamične visine
            rowRenderer={rowRenderer}
          >
            {/* {({ index, style, parent }) => (
              <Row index={index} style={style} parent={parent} />
            )} */}
          </List>
        )}
      </AutoSizer>
    </div>
  );
}

export default Table;
